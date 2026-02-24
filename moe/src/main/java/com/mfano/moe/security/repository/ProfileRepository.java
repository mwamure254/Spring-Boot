package com.mfano.moe.security.repository;

import com.mfano.moe.security.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    public Profile findByUserId(Long userId);
}
