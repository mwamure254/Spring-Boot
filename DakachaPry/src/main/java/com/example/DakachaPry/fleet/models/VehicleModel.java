package com.example.DakachaPry.fleet.models;

import com.example.DakachaPry.parameters.models.CommonObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.Entity;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class VehicleModel extends CommonObject {

}
