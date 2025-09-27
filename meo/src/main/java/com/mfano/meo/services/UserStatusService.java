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
	private UserStatusRepository userStatusRepository;

	// Get All UserStatuss
	public List<UserStatus> findAll() {
		return userStatusRepository.findAll();
	}

	// Get UserStatus By Id
	public Optional<UserStatus> findById(int id) {
		return userStatusRepository.findById(id);
	}

	// Delete UserStatus
	public void delete(int id) {
		userStatusRepository.deleteById(id);
	}

	// Update UserStatus
	public void save(UserStatus userStatus) {
		userStatusRepository.save(userStatus);
	}
    
}
