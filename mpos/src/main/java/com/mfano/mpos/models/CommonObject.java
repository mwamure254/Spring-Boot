package com.mfano.mpos.models;

import java.time.LocalDateTime;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

import com.mfano.mpos.config.CustomUserDetails;

@Data
@MappedSuperclass
public abstract class CommonObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String createdBy;
    @Column(nullable = false)
    protected boolean active;

    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    @Column(unique = true, nullable = false)
    protected String name;
    protected String description;

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

    @PrePersist
    private void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = createdAt;
        this.active = true;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails userDetails) {
            this.createdBy = userDetails.getEmail();
        }
    }

    @PreUpdate
    private void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
