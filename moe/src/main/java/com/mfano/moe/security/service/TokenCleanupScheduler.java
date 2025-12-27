package com.mfano.moe.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.mfano.moe.security.model.VerificationToken;
import com.mfano.moe.security.repository.VerificationTokenRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenCleanupScheduler {

    private final VerificationTokenRepository tokenRepo;

    @Scheduled(cron = "0 0 2 * * *") // daily at 2 AM
    public void deleteExpiredTokens() {
        List<VerificationToken> expiredTokens = tokenRepo.findAll().stream()
                .filter(VerificationToken::isExpired)
                .toList();

        if (!expiredTokens.isEmpty()) {
            tokenRepo.deleteAll(expiredTokens);
            System.out.println("🧹 Deleted " + expiredTokens.size() + " expired verification tokens");
        }
    }
}
