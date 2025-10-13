package com.mfano.moe.security.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mfano.moe.security.Auditable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Station extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String level;
    private String location;
    private String sponsor;
    private String knec;
    private String nemis;
    private String registration;
    private String type;

    @OneToOne
    @JoinColumn(name = "stationstatusid", insertable = false, updatable = false)
    private StationStatus stationStatus;
    private Integer stationstatusid;

    public Station(Integer id, String name, String level, String location, String sponsor, String registration,
            String type, StationStatus stationStatus, Integer stationstatusid, String knec, String nemis) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.location = location;
        this.sponsor = sponsor;
        this.registration = registration;
        this.type = type;
        this.stationStatus = stationStatus;
        this.stationstatusid = stationstatusid;
        this.knec = knec;
        this.nemis = nemis;
    }

    public Station() {
    }

}
