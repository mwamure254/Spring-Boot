package com.mfano.meo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfano.meo.models.Profile;
import com.mfano.meo.repositories.ProfileRepository;

@Service
public class ProfileService {
	@Autowired
	private ProfileRepository profileRepository;

	// Get All Profiles
	public List<Profile> findAll() {
		return profileRepository.findAll();
	}

	// Get Profile By Id
	public Optional<Profile> findById(Long id) {
		return profileRepository.findById(id);
	}

	// Delete Profile
	public void delete(Long id) {
		profileRepository.deleteById(id);
	}

	// Update Profile
	public void save(Profile profile) {
		profileRepository.save(profile);
	}
    
}
