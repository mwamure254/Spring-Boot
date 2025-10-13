package com.mfano.moe.security.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mfano.moe.security.Auditable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ServiceBoard extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "userprofileid", insertable = false, updatable = false)
    private UserProfile userProfile;
    private Integer userprofileid;

    @ManyToOne
    @JoinColumn(name = "stationid", insertable = false, updatable = false)
    private Station station;
    private Integer stationid;

    @ManyToOne
    @JoinColumn(name = "servicestatusid", insertable = false, updatable = false)
    private ServiceStatus serviceStatus;
    private Integer servicestatusid;

}
