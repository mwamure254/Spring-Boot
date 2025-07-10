package com.mfano.meo.models;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ServiceBoard extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private LocalDate starDate = LocalDate.now();
	private LocalDate lastDate;
	private String comments;


    @ManyToOne
	@JoinColumn(name = "userid", insertable = false, updatable = false)
	private User user;
	private Integer userid;
	
    @ManyToOne
	@JoinColumn(name = "stationid", insertable = false, updatable = false)
	private Station station;
	private Integer stationid;
	
    @ManyToOne
	@JoinColumn(name = "servicestatusid", insertable = false, updatable = false)
	private ServiceStatus serviceStatus;
	private Integer servicestatusid;

}
