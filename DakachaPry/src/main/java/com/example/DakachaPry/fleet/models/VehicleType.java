package com.example.DakachaPry.fleet.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.example.DakachaPry.parameters.models.CommonObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.Entity;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class VehicleType extends CommonObject {

}
