package com.mfano.mfano.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfano.mfano.models.VehicleHire;
import com.mfano.mfano.repositories.VehicleHireRepository;

@Service
public class VehicleHireService {
	@Autowired
	private VehicleHireRepository vehicleHireRepository;

	// Get All VehicleHires
	public List<VehicleHire> findAll() {
		return vehicleHireRepository.findAll();
	}

	// Get VehicleHire By Id
	public Optional<VehicleHire> findById(int id) {
		return vehicleHireRepository.findById(id);
	}

	// Delete VehicleHire
	public void delete(int id) {
		vehicleHireRepository.deleteById(id);
	}

	// Update VehicleHire
	public void save(VehicleHire vehicleHire) {
		vehicleHireRepository.save(vehicleHire);
	}
}
