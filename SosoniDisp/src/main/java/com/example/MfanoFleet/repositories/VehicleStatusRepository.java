package com.example.MfanoFleet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MfanoFleet.models.VehicleStatus;

@Repository
public interface VehicleStatusRepository extends JpaRepository<VehicleStatus, Integer> {

}