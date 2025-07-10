package com.mfano.meo.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	private String firstName;
	private String lastName;
	private String otherName;

	private String title;
	private String NID;
	private String gender;
	private String address;
	private String phone;
	private String email;
	private String maritalStaus;
	private String image;

    @OneToOne
    @JoinColumn(name = "userid", insertable = false, updatable = false)
    private User user;
    private Integer userid;
}
