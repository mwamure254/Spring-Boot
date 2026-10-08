package com.mfano.mpos.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "branches")
@Setter
@Getter
@NoArgsConstructor
public class Branch extends CommonObject {
    @Column(nullable = true, unique = true)
    private String code;

    private String location;
    private String manager;
    private String contact;
    private String phone;
    private String email;
    private String address;
}
