package com.mfano.moe.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Institution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // institution
    private String name;
    private String knec;
    private String ems;
    private String registration;
    private String ippd;
    // address
    private String email;
    private String phone;
    private String county;
    private String subcounty;
    private String zone;
    // administration
    private String location;
    private String sublocation;
    private String ward;
    private String constituency;
   
    // ── Relationships (only keep the object reference) ────
    @ManyToOne(fetch = FetchType.LAZY)   // ← usually good choice
    @JoinColumn(name = "icategoryid")
    private ICategory iCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ilevelid")
    private ILevel iLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "istatusid")
    private IStatus iStatus;

}