package com.um.springbootprojstructure.dto;

import jakarta.validation.constraints.NotBlank;

public class RefreshRequest {
    /**
     * Optional: allow providing refresh token in body.
     * If omitted, controller will read Authorization Bearer token.
     */
    private String refreshToken;

    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
}
