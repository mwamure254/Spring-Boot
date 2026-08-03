package com.mfano.moe.security.repository;

import com.mfano.moe.security.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User, Long> {
  User findByEmail(String email);
}
