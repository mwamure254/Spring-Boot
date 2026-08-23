package com.mfano.mpos.models;

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
public class Branch extends CommonObject {
    private String location;
    private String manager;
    private String contact;  
    private String email;
    private String address;
    private String createdBy;
}
