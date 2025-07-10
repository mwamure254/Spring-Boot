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
    protected LocalDateTime CreatedeAt = LocalDateTime.now();

    @CreatedBy
    protected U CreatedBy;

    @LastModifiedDate
    protected LocalDateTime LastUpdatedAt;

    @LastModifiedBy
    protected U LastModifiedBy;

}
