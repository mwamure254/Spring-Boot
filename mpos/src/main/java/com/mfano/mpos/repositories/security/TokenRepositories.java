package com.mfano.mpos.repositories.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfano.mpos.models.security.User;
import com.mfano.mpos.models.security.VerificationToken;
import java.util.List;

public interface TokenRepositories extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByToken(String token);

    void deleteByUserId(Long id);

    void deleteByToken(String token);

    void deleteByUserIdAndToken(Long userId, String token);

    void deleteByUserIdAndTokenAndExpiryDateLessThan(Long userId, String token, java.util.Date date);

    List<VerificationToken> findByUser(User user);
}
