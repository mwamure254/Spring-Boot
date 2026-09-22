package com.mfano.mpos.utils.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.mfano.mpos.models.security.VerificationToken;
import com.mfano.mpos.repositories.security.TokenRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenCleaner {

    private final TokenRepository tokenRepo;

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