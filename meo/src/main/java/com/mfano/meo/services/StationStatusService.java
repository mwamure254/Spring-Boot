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
	private StationStatusRepository stationStatusRepository;

	// Get All StationStatuss
	public List<StationStatus> findAll() {
		return stationStatusRepository.findAll();
	}

	// Get StationStatus By Id
	public Optional<StationStatus> findById(int id) {
		return stationStatusRepository.findById(id);
	}

	// Delete StationStatus
	public void delete(int id) {
		stationStatusRepository.deleteById(id);
	}

	// Update StationStatus
	public void save(StationStatus stationStatus) {
		stationStatusRepository.save(stationStatus);
	}
    
}
