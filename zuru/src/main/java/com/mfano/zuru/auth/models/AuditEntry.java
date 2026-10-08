package com.mfano.zuru.auth.models;

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
