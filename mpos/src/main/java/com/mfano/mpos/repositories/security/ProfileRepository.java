package com.mfano.mpos.repositories.security;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfano.mpos.models.security.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long>{ 
        public Profile findByUserId(Long userId);
}
