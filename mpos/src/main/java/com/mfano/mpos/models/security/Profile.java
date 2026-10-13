package com.mfano.mpos.models.security;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.mfano.mpos.models.BaseObject;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Profile extends BaseObject{
    // personal
    private String about;
    private String fin;
    private String lan;
    private String other;
    private String SN;
    private String phone;
    private String email;
    // TSC or PF Number
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

    @JoinColumn(name = "user_id", unique = true)
    private Long user;
}