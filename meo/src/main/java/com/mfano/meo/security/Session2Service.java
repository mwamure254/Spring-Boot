package com.mfano.meo.security;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Session2Service {
	@Autowired
	private Session2Repository session2Repository;
	// Get All Session2s
	public List<Session2> findAll() {
		return session2Repository.findAll();
	}

	// Get Session2 By Id
	public Optional<Session2> findById(Long id) {
		return session2Repository.findById(id);
	}

	// Delete Session2
	public void delete(Long id) {
		session2Repository.deleteById(id);
	}

	// Update Session2
	public void save(Session2 session2) {
		session2Repository.save(session2);
	}

}
