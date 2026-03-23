package com.um.springbootprojstructure.dto;

import jakarta.validation.constraints.Email;

public class ValidateDirectoryRequest {

    /**
     * Common identity attributes. Provide at least one.
     */
    private String username;

    @Email
    private String email;

    private String employeeId;

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getEmployeeId() { return employeeId; }

    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
}
