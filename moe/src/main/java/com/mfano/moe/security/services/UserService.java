package com.mfano.moe.security.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mfano.moe.security.models.User;
import com.mfano.moe.security.repositories.UserRepository;

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
	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public void save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
}
