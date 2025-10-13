package com.mfano.moe.security;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U> {

    @CreatedDate
    protected LocalDateTime sreatedeAt = LocalDateTime.now();
    @CreatedBy
    protected U createdBy;
    @LastModifiedDate
    protected LocalDateTime lastUpdatedAt;
    @LastModifiedBy
    protected U lastModifiedBy;

    public LocalDateTime getSreatedeAt() {
        return sreatedeAt;
    }

    public void setSreatedeAt(LocalDateTime sreatedeAt) {
        this.sreatedeAt = sreatedeAt;
    }

    public U getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(U createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public U getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(U lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
    
}
