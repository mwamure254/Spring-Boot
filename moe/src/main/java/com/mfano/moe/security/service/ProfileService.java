package com.mfano.moe.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfano.moe.security.model.Profile;
import com.mfano.moe.security.repository.ProfileRepository;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    // Get All Profiles
    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    // Get Profile By Id
    public Profile findById(Long id) {
        return profileRepository.findById(id).orElse(null);
    }

    // Get Profile By Id
    public Profile findByUserId(Long userId) {
        return profileRepository.findByUserId(userId);
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
