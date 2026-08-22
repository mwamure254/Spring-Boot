package com.mfano.blog.models.security;

import com.mfano.blog.models.BaseModel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuditEntry extends BaseModel {private String action;
    private String performedBy;
    private String details;
}
