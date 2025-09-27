package com.mfano.meo.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
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

}
