package com.mfano.moe.security.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
    // personal
    private String about;
    private String fin;
    private String lan;
    private String other;
    private String IDN;
    private String phone;
    // TSC or PF Number
    private String SN;
    private String image;
    private String gender;
    // address
    private String county;
    private String address;
    private String designation;
    // Social Media
    private String twitter;
    private String facebook;
    private String linkedin;
    private String instagram;

    @OneToOne
    @JoinColumn(name = "userid", nullable = false, insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
    private Long userid;
}
