package com.mfano.blog.repositories.security;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfano.blog.models.security.User;

public interface UserRepository extends JpaRepository<User, Long> {
  public User findByEmail(String email);

  public User findByUsername(String username);
}
