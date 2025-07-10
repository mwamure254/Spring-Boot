package com.mfano.meo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfano.meo.models.ServiceStatus;
import com.mfano.meo.repositories.ServiceStatusRepository;

@Service
public class ServiceStatusService {
	@Autowired
	private ServiceStatusRepository ServiceStatusRepository;

	// Get All ServiceStatuss
	public List<ServiceStatus> findAll() {
		return ServiceStatusRepository.findAll();
	}

	// Get ServiceStatus By Id
	public Optional<ServiceStatus> findById(Long id) {
		return ServiceStatusRepository.findById(id);
	}

	// Delete ServiceStatus
	public void delete(Long id) {
		ServiceStatusRepository.deleteById(id);
	}

	// Update ServiceStatus
	public void save(ServiceStatus ServiceStatus) {
		ServiceStatusRepository.save(ServiceStatus);
	}
    
}
