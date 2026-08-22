package com.mfano.blog.repositories.security;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfano.blog.models.security.VerificationToken;

public interface TokenRepository extends JpaRepository<VerificationToken, Long> {
  Optional<VerificationToken> findByToken(String token);

  void deleteByUserId(Long userId);

  void deleteByExpiryDateBefore(LocalDateTime expiryTime);
}
