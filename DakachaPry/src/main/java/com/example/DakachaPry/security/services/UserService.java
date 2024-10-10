package com.example.DakachaPry.security.services;

import com.example.DakachaPry.security.models.User;
import com.example.DakachaPry.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	// Get All Users
	public List<User> findAll() {
		return userRepository.findAll();
	}

	// Get User By Id
	public User findById(int id) {
		return userRepository.findById(id).orElse(null);
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
