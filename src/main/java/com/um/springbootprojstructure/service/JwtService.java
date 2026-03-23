package com.um.springbootprojstructure.service;

import com.um.springbootprojstructure.entity.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    private final String issuer;
    private final Key key;
    private final long accessTtlSeconds;
    private final long refreshTtlSeconds;

    public JwtService(
            @Value("${app.jwt.issuer}") String issuer,
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.access-ttl-seconds}") long accessTtlSeconds,
            @Value("${app.jwt.refresh-ttl-seconds}") long refreshTtlSeconds
    ) {
        this.issuer = issuer;
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTtlSeconds = accessTtlSeconds;
        this.refreshTtlSeconds = refreshTtlSeconds;
    }

    public long getAccessTtlSeconds() {
        return accessTtlSeconds;
    }

    public long getRefreshTtlSeconds() { return refreshTtlSeconds; }

    public String issueAccessToken(String publicRef, Role role) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(accessTtlSeconds);

        return Jwts.builder()
                .issuer(issuer)
                .subject(publicRef)
                .claims(Map.of(
                        "typ", "access",
                        "role", role.name()
                ))
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .signWith(key)
                .compact();
    }

    public String issueRefreshToken(String publicRef, String refreshJti) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(refreshTtlSeconds);

        return Jwts.builder()
                .issuer(issuer)
                .subject(publicRef)
                .id(refreshJti)
                .claims(Map.of("typ", "refresh"))
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .signWith(key)
                .compact();
    }

    public Jws<Claims> parseAndValidate(String jwt) throws JwtException {
        return Jwts.parser()
                .verifyWith((javax.crypto.SecretKey) key)
                .requireIssuer(issuer)
                .build()
                .parseSignedClaims(jwt);
    }

    public void assertTokenType(Jws<Claims> jws, String expectedType) {
        String typ = jws.getPayload().get("typ", String.class);
        if (typ == null || !typ.equals(expectedType)) {
            throw new JwtException("Invalid token type");
        }
    }
}
