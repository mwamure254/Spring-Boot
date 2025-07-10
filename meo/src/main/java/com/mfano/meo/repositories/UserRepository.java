package com.mfano.meo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mfano.meo.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
 Optional<User> findByEmail(String email);
}
