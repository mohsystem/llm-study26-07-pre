package com.um.springbootprojstructure.dto;

import com.um.springbootprojstructure.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CreateUserRequest {

    @NotBlank
    private String fullName;

    @NotBlank
    @Email
    private String email;

    // Optional; if null -> USER
    private Role role;

    private Boolean active;

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public Boolean getActive() {
        return active;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
