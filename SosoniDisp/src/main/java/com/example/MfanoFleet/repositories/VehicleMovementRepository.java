package com.example.MfanoFleet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MfanoFleet.models.Client;

@Repository
public interface VehicleMovementRepository extends JpaRepository<Client, Integer> {

}