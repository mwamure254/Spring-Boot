package com.example.DakachaPry.fleet.repositories;

import com.example.DakachaPry.fleet.models.VehicleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleModelRepository extends JpaRepository<VehicleModel, Integer> {

}
