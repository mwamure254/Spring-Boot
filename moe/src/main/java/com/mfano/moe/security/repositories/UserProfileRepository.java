package com.mfano.moe.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfano.moe.security.models.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
    
}
