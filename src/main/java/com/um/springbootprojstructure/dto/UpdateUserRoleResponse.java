package com.um.springbootprojstructure.dto;

import com.um.springbootprojstructure.entity.Role;

import java.time.Instant;

public class UpdateUserRoleResponse {
    private Long id;
    private String publicRef;
    private Role role;
    private Instant updatedAt;

    public Long getId() { return id; }
    public String getPublicRef() { return publicRef; }
    public Role getRole() { return role; }
    public Instant getUpdatedAt() { return updatedAt; }

    public void setId(Long id) { this.id = id; }
    public void setPublicRef(String publicRef) { this.publicRef = publicRef; }
    public void setRole(Role role) { this.role = role; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
