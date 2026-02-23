package com.mfano.moe.models;

import jakarta.persistence.Entity;
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
    // relationships
    @ManyToOne
	@JoinColumn(name = "icategoryid", insertable = false, updatable = false)
	private ICategory iCategory;
	private Integer icategoryid;

    @ManyToOne
	@JoinColumn(name = "ilevelid", insertable = false, updatable = false)
	private ILevel iLevel;
	private Integer ilevelid;

    @ManyToOne
	@JoinColumn(name = "istatusid", insertable = false, updatable = false)
	private IStatus iStatus;
	private Integer istatusid;

}
