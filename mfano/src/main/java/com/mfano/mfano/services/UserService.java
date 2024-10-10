package com.mfano.mfano.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mfano.mfano.models.User;
import com.mfano.mfano.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	// Get All Users
	public List<User> findAll() {
		return userRepository.findAll();
	}

	// Get User By Id
	public Optional<User> findById(int id) {
		return userRepository.findById(id);
	}

	// Delete User
	public void delete(int id) {
		userRepository.deleteById(id);
	}

	// Update User
	@Autowired
	private PasswordEncoder passwordEncoder;

	public void save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
}
