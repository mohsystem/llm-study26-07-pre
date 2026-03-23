package com.um.springbootprojstructure.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "password_rules")
public class PasswordRules {

    /**
     * Single-row table approach: id=1 is the active ruleset.
     */
    @Id
    private Long id = 1L;

    @Column(nullable = false)
    private int minLength = 12;

    @Column(nullable = false)
    private boolean requireUppercase = true;

    @Column(nullable = false)
    private boolean requireLowercase = true;

    @Column(nullable = false)
    private boolean requireDigit = true;

    @Column(nullable = false)
    private boolean requireSpecial = true;

    /**
     * A set of allowed special characters (simple policy).
     * If requireSpecial=true, at least one of these must appear.
     */
    @Column(nullable = false)
    private String allowedSpecials = "!@#$%^&*()_+-=[]{}|;:,.<>?";

    @Column(nullable = false)
    private boolean disallowCommonPasswords = false;

    @Column(nullable = false)
    private boolean disallowEmailAsPassword = true;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    @PreUpdate
    void touch() {
        this.updatedAt = Instant.now();
        if (this.id == null) this.id = 1L;
    }

    public Long getId() { return id; }
    public int getMinLength() { return minLength; }
    public boolean isRequireUppercase() { return requireUppercase; }
    public boolean isRequireLowercase() { return requireLowercase; }
    public boolean isRequireDigit() { return requireDigit; }
    public boolean isRequireSpecial() { return requireSpecial; }
    public String getAllowedSpecials() { return allowedSpecials; }
    public boolean isDisallowCommonPasswords() { return disallowCommonPasswords; }
    public boolean isDisallowEmailAsPassword() { return disallowEmailAsPassword; }
    public Instant getUpdatedAt() { return updatedAt; }

    public void setId(Long id) { this.id = id; }
    public void setMinLength(int minLength) { this.minLength = minLength; }
    public void setRequireUppercase(boolean requireUppercase) { this.requireUppercase = requireUppercase; }
    public void setRequireLowercase(boolean requireLowercase) { this.requireLowercase = requireLowercase; }
    public void setRequireDigit(boolean requireDigit) { this.requireDigit = requireDigit; }
    public void setRequireSpecial(boolean requireSpecial) { this.requireSpecial = requireSpecial; }
    public void setAllowedSpecials(String allowedSpecials) { this.allowedSpecials = allowedSpecials; }
    public void setDisallowCommonPasswords(boolean disallowCommonPasswords) { this.disallowCommonPasswords = disallowCommonPasswords; }
    public void setDisallowEmailAsPassword(boolean disallowEmailAsPassword) { this.disallowEmailAsPassword = disallowEmailAsPassword; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
