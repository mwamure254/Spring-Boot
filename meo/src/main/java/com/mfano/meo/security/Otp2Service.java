package com.mfano.meo.security;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Otp2Service {
	@Autowired
	private Otp2Repository otp2Repository;
	// Get All Otp2s
	public List<Otp2> findAll() {
		return otp2Repository.findAll();
	}

	// Get Otp2 By Id
	public Optional<Otp2> findById(Long id) {
		return otp2Repository.findById(id);
	}

	// Delete Otp2
	public void delete(Long id) {
		otp2Repository.deleteById(id);
	}

	// Update Otp2
	public void save(Otp2 Otp2) {
		otp2Repository.save(Otp2);
	}

}
