package com.mfano.moe.security.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//personal
    private String fin;
    private String lan;
    private String other;
    private String IDN;
    private String phone;
//TSC or PF Number
    private String serviceNumber;
//address
    private String homeCounty;
    private String homeSubCounty;
    private String homeConstituency;
    private String homeSubLocation;
    private String homeWard;

}
