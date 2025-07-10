package com.mfano.meo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfano.meo.models.UserStatus;
import com.mfano.meo.repositories.UserStatusRepository;

@Service
public class UserStatusService {
	@Autowired
	private UserStatusRepository UserStatusRepository;

	// Get All UserStatuss
	public List<UserStatus> findAll() {
		return UserStatusRepository.findAll();
	}

	// Get UserStatus By Id
	public Optional<UserStatus> findById(Long id) {
		return UserStatusRepository.findById(id);
	}

	// Delete UserStatus
	public void delete(Long id) {
		UserStatusRepository.deleteById(id);
	}

	// Update UserStatus
	public void save(UserStatus UserStatus) {
		UserStatusRepository.save(UserStatus);
	}
    
}
