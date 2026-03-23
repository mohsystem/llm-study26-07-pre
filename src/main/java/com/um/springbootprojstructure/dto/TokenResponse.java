package com.um.springbootprojstructure.dto;

public class TokenResponse {
    private String tokenType = "Bearer";
    private String accessToken;
    private long expiresInSeconds;
    private String refreshToken;

    public TokenResponse() {}

    public TokenResponse(String accessToken, long expiresInSeconds, String refreshToken) {
        this.accessToken = accessToken;
        this.expiresInSeconds = expiresInSeconds;
        this.refreshToken = refreshToken;
    }

    public String getTokenType() { return tokenType; }
    public String getAccessToken() { return accessToken; }
    public long getExpiresInSeconds() { return expiresInSeconds; }
    public String getRefreshToken() { return refreshToken; }

    public void setTokenType(String tokenType) { this.tokenType = tokenType; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    public void setExpiresInSeconds(long expiresInSeconds) { this.expiresInSeconds = expiresInSeconds; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
}
