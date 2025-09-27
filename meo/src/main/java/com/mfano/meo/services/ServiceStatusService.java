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
	private ServiceStatusRepository serviceStatusRepository;

	// Get All ServiceStatuss
	public List<ServiceStatus> findAll() {
		return serviceStatusRepository.findAll();
	}

	// Get ServiceStatus By Id
	public Optional<ServiceStatus> findById(int id) {
		return serviceStatusRepository.findById(id);
	}

	// Delete ServiceStatus
	public void delete(int id) {
		serviceStatusRepository.deleteById(id);
	}

	// Update ServiceStatus
	public void save(ServiceStatus serviceStatus) {
		serviceStatusRepository.save(serviceStatus);
	}
    
}
