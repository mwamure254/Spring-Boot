package com.mfano.mpos.models.security;

import com.mfano.mpos.models.BaseObject;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "audits")
@Setter
@Getter
@NoArgsConstructor
public class AuditEntry extends BaseObject {
    private String action;
    private String details;
}
