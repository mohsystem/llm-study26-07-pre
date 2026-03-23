package com.um.springbootprojstructure.service;

import com.um.springbootprojstructure.dto.TokenResponse;
import com.um.springbootprojstructure.entity.Session;
import com.um.springbootprojstructure.repository.SessionRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class AuthService {

    private final JwtService jwtService;
    private final UserService userService;
    private final SessionRepository sessionRepository;

    public AuthService(JwtService jwtService, UserService userService, SessionRepository sessionRepository) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.sessionRepository = sessionRepository;
    }

    /**
     * Validates refresh token + session state, then rotates refresh token and issues a new access token.
     */
    @Transactional
    public TokenResponse refresh(String refreshToken) throws JwtException {
        Jws<Claims> jws = jwtService.parseAndValidate(refreshToken);
        jwtService.assertTokenType(jws, "refresh");

        String publicRef = jws.getPayload().getSubject();
        String jti = jws.getPayload().getId();
        if (jti == null || jti.isBlank()) {
            throw new JwtException("Missing jti on refresh token");
        }

        Session session = sessionRepository.findByRefreshJti(jti)
                .orElseThrow(() -> new JwtException("Session not found (token revoked or unknown)"));

        if (session.isRevoked()) {
            throw new JwtException("Session revoked");
        }

        var user = userService.getByPublicRef(publicRef);

        // rotate: revoke old, create new session+jti
        session.setRevoked(true);
        session.setRevokedAt(Instant.now());
        sessionRepository.save(session);

        String newJti = UUID.randomUUID().toString();
        Session newSession = new Session(user.getId(), newJti);
        sessionRepository.save(newSession);

        String newAccess = jwtService.issueAccessToken(user.getPublicRef(), user.getRole());
        String newRefresh = jwtService.issueRefreshToken(user.getPublicRef(), newJti);

        return new TokenResponse(newAccess, jwtService.getAccessTtlSeconds(), newRefresh);
    }

    /**
     * Logout invalidates the session identified by refresh token.
     */
    @Transactional
    public void logout(String refreshToken) throws JwtException {
        Jws<Claims> jws = jwtService.parseAndValidate(refreshToken);
        jwtService.assertTokenType(jws, "refresh");

        String jti = jws.getPayload().getId();
        if (jti == null || jti.isBlank()) {
            throw new JwtException("Missing jti on refresh token");
        }

        sessionRepository.findByRefreshJti(jti).ifPresent(session -> {
            if (!session.isRevoked()) {
                session.setRevoked(true);
                session.setRevokedAt(Instant.now());
                sessionRepository.save(session);
            }
        });
    }

    /**
     * Helper you will likely use for login later: create initial session + tokens.
     */
    @Transactional
    public TokenResponse createSessionTokensForUser(String publicRef) {
        var user = userService.getByPublicRef(publicRef);

        String jti = UUID.randomUUID().toString();
        sessionRepository.save(new Session(user.getId(), jti));

        String access = jwtService.issueAccessToken(user.getPublicRef(), user.getRole());
        String refresh = jwtService.issueRefreshToken(user.getPublicRef(), jti);

        return new TokenResponse(access, jwtService.getAccessTtlSeconds(), refresh);
    }
}
