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
	private ServiceBoardRepository ServiceBoardRepository;

	// Get All ServiceBoards
	public List<ServiceBoard> findAll() {
		return ServiceBoardRepository.findAll();
	}

	// Get ServiceBoard By Id
	public Optional<ServiceBoard> findById(Long id) {
		return ServiceBoardRepository.findById(id);
	}

	// Delete ServiceBoard
	public void delete(Long id) {
		ServiceBoardRepository.deleteById(id);
	}

	// Update ServiceBoard
	public void save(ServiceBoard ServiceBoard) {
		ServiceBoardRepository.save(ServiceBoard);
	}
    
}
