package com.mfano.meo.hois;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService1 {
	@Autowired
	private UserRepository1 clientRepository;

	// Get All Clients
	public List<User1> findAll() {
		return clientRepository.findAll();
	}

	// Get Client By Id
	public Optional<User1> findById(int id) {
		return clientRepository.findById(id);
	}

	// Delete Client
	public void delete(int id) {
		clientRepository.deleteById(id);
	}

	// Update Client
	public void save(User1 client) {
		clientRepository.save(client);
	}
}
