package com.mfano.meo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfano.meo.models.Station;
import com.mfano.meo.repositories.StationRepository;

@Service
public class StationService {
	@Autowired
	private StationRepository stationRepository;

	// Get All Stations
	public List<Station> findAll() {
		return stationRepository.findAll();
	}

	// Get Station By Id
	public Optional<Station> findById(Long id) {
		return stationRepository.findById(id);
	}

	// Delete Station
	public void delete(Long id) {
		stationRepository.deleteById(id);
	}

	// Update Station
	public void save(Station station) {
		stationRepository.save(station);
	}
    
}
