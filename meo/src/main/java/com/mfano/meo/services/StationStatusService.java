package com.mfano.meo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfano.meo.models.StationStatus;
import com.mfano.meo.repositories.StationStatusRepository;

@Service
public class StationStatusService {
	@Autowired
	private StationStatusRepository StationStatusRepository;

	// Get All StationStatuss
	public List<StationStatus> findAll() {
		return StationStatusRepository.findAll();
	}

	// Get StationStatus By Id
	public Optional<StationStatus> findById(Long id) {
		return StationStatusRepository.findById(id);
	}

	// Delete StationStatus
	public void delete(Long id) {
		StationStatusRepository.deleteById(id);
	}

	// Update StationStatus
	public void save(StationStatus StationStatus) {
		StationStatusRepository.save(StationStatus);
	}
    
}
