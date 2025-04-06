package com.mfano.meo.hois;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	private UserRepository clientRepository;

	// Get All Clients
	public List<User> findAll() {
		return clientRepository.findAll();
	}

	// Get Client By Id
	public Optional<User> findById(int id) {
		return clientRepository.findById(id);
	}

	// Delete Client
	public void delete(int id) {
		clientRepository.deleteById(id);
	}

	// Update Client
	public void save(User client) {
		clientRepository.save(client);
	}
}
