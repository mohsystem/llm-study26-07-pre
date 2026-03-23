package com.um.springbootprojstructure.dto;

import com.um.springbootprojstructure.entity.Role;

import java.time.Instant;

public class UserResponse {
    private Long id; // internal (optional; you can remove later if you don't want to expose it)
    private String publicRef;

    private String fullName;
    private String email;
    private Role role;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;

    public Long getId() { return id; }
    public String getPublicRef() { return publicRef; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public Role getRole() { return role; }
    public boolean isActive() { return active; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    public void setId(Long id) { this.id = id; }
    public void setPublicRef(String publicRef) { this.publicRef = publicRef; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setRole(Role role) { this.role = role; }
    public void setActive(boolean active) { this.active = active; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
