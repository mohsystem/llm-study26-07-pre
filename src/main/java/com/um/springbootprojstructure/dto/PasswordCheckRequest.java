package com.um.springbootprojstructure.dto;

import jakarta.validation.constraints.NotBlank;

public class PasswordCheckRequest {

    @NotBlank
    private String password;

    /**
     * Optional context attributes used by certain rules (e.g., disallowEmailAsPassword).
     */
    private String email;

    public String getPassword() { return password; }
    public String getEmail() { return email; }

    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }
}
