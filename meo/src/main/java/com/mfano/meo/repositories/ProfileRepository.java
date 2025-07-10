package com.mfano.meo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mfano.meo.models.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long>{

}
