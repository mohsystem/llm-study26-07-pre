package com.um.springbootprojstructure.controller;

import com.um.springbootprojstructure.dto.LogoutResponse;
import com.um.springbootprojstructure.dto.RefreshRequest;
import com.um.springbootprojstructure.dto.TokenResponse;
import com.um.springbootprojstructure.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/refresh")
    public TokenResponse refresh(@Valid @RequestBody(required = false) RefreshRequest body,
                                 HttpServletRequest request) {
        String token = resolveBearerOrBodyToken(body, request);

        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("Missing refresh token");
        }

        return authService.refresh(token);
    }

    @PostMapping("/logout")
    public LogoutResponse logout(@Valid @RequestBody(required = false) RefreshRequest body,
                                 HttpServletRequest request) {
        String token = resolveBearerOrBodyToken(body, request);
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("Missing refresh token");
        }

        authService.logout(token);
        return new LogoutResponse(true, Instant.now());
    }

    private String resolveBearerOrBodyToken(RefreshRequest body, HttpServletRequest request) {
        if (body != null && body.getRefreshToken() != null && !body.getRefreshToken().isBlank()) {
            return body.getRefreshToken();
        }
        String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (auth != null && auth.startsWith("Bearer ")) {
            return auth.substring("Bearer ".length());
        }
        return null;
    }
}
