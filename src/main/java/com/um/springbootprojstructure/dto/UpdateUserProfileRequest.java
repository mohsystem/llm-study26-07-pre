package com.um.springbootprojstructure.dto;

import jakarta.validation.constraints.Email;

public class UpdateUserProfileRequest {

    // Allowed profile fields (adjust as needed)
    private String fullName;

    @Email
    private String email;

    public String getFullName() { return fullName; }
    public String getEmail() { return email; }

    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
}
