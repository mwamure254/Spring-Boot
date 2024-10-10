package com.example.DakachaPry.fleet.repositories;

import com.example.DakachaPry.fleet.models.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleTypeRepository extends JpaRepository<VehicleType, Integer> {

}
