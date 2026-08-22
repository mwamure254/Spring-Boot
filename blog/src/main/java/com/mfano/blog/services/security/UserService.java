package com.mfano.blog.services.security;

import java.time.LocalDateTime;
import java.util.Optional;
//import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mfano.blog.models.security.Role;
import com.mfano.blog.models.security.User;
import com.mfano.blog.models.security.VerificationToken;
import com.mfano.blog.repositories.security.TokenRepository;
import com.mfano.blog.repositories.security.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final TokenRepository tokenRepository;
    private final MailService emailService;

    @Value("${app.base-url}")
    private String appBaseUrl;

    @Transactional
    public User registerUser(String email, String rawPassword, Set<Role> role) {

        if (userRepository.findByEmail(email) != null) {
            throw new RuntimeException("Email already in use");
        }

        if (role == null || role.isEmpty()) {
            Role userRole = new Role();
            userRole.setName("USER");
            role = Set.of(userRole);
        }

        User user = new User();
        
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRoles(role);

        userRepository.save(user);
        // createAndSendToken(user);
        return user;
    }

    // Get User By Id
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void createAndSendToken(User user) {
        String token = UUID.randomUUID().toString();
        LocalDateTime expiry = LocalDateTime.now().plusHours(24);
        VerificationToken vt = new VerificationToken();
        vt.setToken(token);
        vt.setExpiryDate(expiry);
        vt.setUser(user);
        tokenRepository.save(vt);
        String link = appBaseUrl + "/verify?token=" + token;
        String subject = "Please verify your email";
        String body = "Hi " + (user.getEmail() == null ? "" : user.getEmail()) + "\n\n"
                + "Please click the link to verify your email:\n" + link + "\n\n"
                + "This link will expire in 24 hours.\n\nThanks!";
        emailService.sendSimpleMessage(user.getEmail(), subject, body);
    }

    public String validateVerificationToken(String token) {
        Optional<VerificationToken> opt = tokenRepository.findByToken(token);
        if (opt.isEmpty())
            return "invalid";
        VerificationToken vt = opt.get();
        if (vt.getExpiryDate().isBefore(LocalDateTime.now())) {
            return "expired";
        }
        User user = vt.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        tokenRepository.delete(vt);
        return "valid";
    }

    // Password reset flow
    public void createPasswordResetToken(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("No user with the email provided");
        }

        tokenRepository.deleteByUserId(user.getId());
        String token = UUID.randomUUID().toString();
        VerificationToken prt = new VerificationToken();
        prt.setToken(token);
        prt.setExpiryDate(LocalDateTime.now().plusHours(2));
        prt.setUser(user);
        tokenRepository.save(prt);
        String link = appBaseUrl + "/reset-password?token=" + token;
        String subject = "Password reset request";
        String body = "Hi " + (user.getEmail() == null ? "" : user.getEmail()) + "\n\n"
                + "Click the link to reset your password: \n" + link + "\n\n"
                + "This link expires in 2 hours.\n\nIf you did not request this, ignore this email.";
        emailService.sendSimpleMessage(user.getEmail(), subject, body);
    }

    public String validatePasswordResetToken(String token) {
        var opt = tokenRepository.findByToken(token);
        if (opt.isEmpty())
            return "invalid";
        var prt = opt.get();
        if (prt.getExpiryDate().isBefore(LocalDateTime.now()))
            return "expired";
        return "valid";
    }

    public Optional<User> getUserByPasswordResetToken(String token) {
        return tokenRepository.findByToken(token).map(VerificationToken::getUser);
    }

    @Transactional
    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        tokenRepository.deleteByUserId(user.getId());
    }

}