package com.mfano.mpos.models.security;

import com.mfano.mpos.models.BaseObject;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuditEntry extends BaseObject {
    private String action;
    private String performedBy;
    private String details;
}
