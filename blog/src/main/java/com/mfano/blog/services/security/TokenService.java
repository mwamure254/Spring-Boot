package com.mfano.blog.services.security;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.mfano.blog.models.security.VerificationToken;
import com.mfano.blog.repositories.security.TokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepo;

    @Scheduled(cron = "0 0 2 * * *")
    public void deleteExpiredTokens() {

        LocalDateTime now = LocalDateTime.now();

        tokenRepo.deleteByExpiryDateBefore(now);

        System.out.println("🧹 Expired verification tokens cleaned up.");
    }
}