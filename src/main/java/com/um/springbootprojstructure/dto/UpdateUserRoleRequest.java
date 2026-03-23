package com.um.springbootprojstructure.dto;

import com.um.springbootprojstructure.entity.Role;
import jakarta.validation.constraints.NotNull;

public class UpdateUserRoleRequest {

    @NotNull
    private Role role;

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
