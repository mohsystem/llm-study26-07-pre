package com.um.springbootprojstructure.dto;

import java.time.Instant;

public class LogoutResponse {
    private boolean success;
    private Instant loggedOutAt;

    public LogoutResponse() {}

    public LogoutResponse(boolean success, Instant loggedOutAt) {
        this.success = success;
        this.loggedOutAt = loggedOutAt;
    }

    public boolean isSuccess() { return success; }
    public Instant getLoggedOutAt() { return loggedOutAt; }

    public void setSuccess(boolean success) { this.success = success; }
    public void setLoggedOutAt(Instant loggedOutAt) { this.loggedOutAt = loggedOutAt; }
}
