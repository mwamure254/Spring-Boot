package com.example.DakachaPry.fleet.services;

import com.example.DakachaPry.fleet.models.VehicleMovement;
import com.example.DakachaPry.fleet.repositories.VehicleMovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleMovementService {

	@Autowired
	private VehicleMovementRepository vehicleMovementRepository;

	// Get All VehicleMovements
	public List<VehicleMovement> findAll() {
		return vehicleMovementRepository.findAll();
	}

	// Get VehicleMovement By Id
	public VehicleMovement findById(int id) {
		return vehicleMovementRepository.findById(id).orElse(null);
	}

	// Delete VehicleMovement
	public void delete(int id) {
		vehicleMovementRepository.deleteById(id);
	}

	// Update VehicleMovement
	public void save(VehicleMovement vehicleMovement) {
		vehicleMovementRepository.save(vehicleMovement);
	}

}
