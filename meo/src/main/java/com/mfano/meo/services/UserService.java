package com.mfano.meo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfano.meo.models.User;
import com.mfano.meo.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	// Get All Users
	public List<User> findAll() {
		return userRepository.findAll();
	}

	// Get User By Id
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

	// Get User By Email
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	// Delete User
	public void delete(Long id) {
		userRepository.deleteById(id);
	}

	// Update User
	public void save(User User) {
		userRepository.save(User);
	}
    
}
