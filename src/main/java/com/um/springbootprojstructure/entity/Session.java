package com.um.springbootprojstructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "sessions",
        indexes = {
                @Index(name = "idx_sessions_refresh_jti", columnList = "refreshJti", unique = true),
                @Index(name = "idx_sessions_user_id", columnList = "userId")
        }
)
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // store a stable reference to the user (publicRef is ok too; id is fine internally)
    @Column(nullable = false)
    private Long userId;

    /**
     * JWT ID for the refresh token (jti claim). Used for revocation/rotation.
     */
    @Column(nullable = false, unique = true, updatable = false, length = 36)
    private String refreshJti;

    @Column(nullable = false)
    private boolean revoked = false;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    private Instant revokedAt;

    @PrePersist
    void onCreate() {
        if (this.refreshJti == null || this.refreshJti.isBlank()) {
            this.refreshJti = UUID.randomUUID().toString();
        }
        this.createdAt = Instant.now();
    }

    public Session() {}

    public Session(Long userId, String refreshJti) {
        this.userId = userId;
        this.refreshJti = refreshJti;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getRefreshJti() { return refreshJti; }
    public boolean isRevoked() { return revoked; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getRevokedAt() { return revokedAt; }

    public void setId(Long id) { this.id = id; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setRefreshJti(String refreshJti) { this.refreshJti = refreshJti; }
    public void setRevoked(boolean revoked) { this.revoked = revoked; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public void setRevokedAt(Instant revokedAt) { this.revokedAt = revokedAt; }
}
