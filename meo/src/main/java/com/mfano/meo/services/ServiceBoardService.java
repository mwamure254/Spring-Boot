package com.mfano.meo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfano.meo.models.ServiceBoard;
import com.mfano.meo.repositories.ServiceBoardRepository;

@Service
public class ServiceBoardService {
	@Autowired
	private ServiceBoardRepository serviceBoardRepository;

	// Get All ServiceBoards
	public List<ServiceBoard> findAll() {
		return serviceBoardRepository.findAll();
	}

	// Get ServiceBoard By Id
	public Optional<ServiceBoard> findById(Long id) {
		return serviceBoardRepository.findById(id);
	}

	// Delete ServiceBoard
	public void delete(Long id) {
		serviceBoardRepository.deleteById(id);
	}

	// Update ServiceBoard
	public void save(ServiceBoard serviceBoard) {
		serviceBoardRepository.save(serviceBoard);
	}
    
}
