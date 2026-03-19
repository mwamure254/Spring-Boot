package com.mfano.moe.security.service;

import com.mfano.moe.security.config.CustomUserDetails;
import com.mfano.moe.security.model.*;
import com.mfano.moe.security.repository.*;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final VerificationTokenRepository tokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final ProfileService profileService;

    @Value("${app.base-url}")
    private String appBaseUrl;

    @Transactional
    public User registerUser(String email, String rawPassword, String role) {
        if (userRepository.findByEmail(email) != null) {
            throw new RuntimeException("Email is already registered");

        }

        User user = new User();

        if (role.isEmpty()) {
            Role userRole = new Role();
            userRole.setName("USER");
            user.setRoles(Set.of(userRole));
        }

        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRoles(Set.of(roleService.findByName(role).get()));
        user.setEnabled(true);

        userRepository.save(user);
        createAndSendToken(user);
        return user;
    }

    public void createAndSendToken(User user) {
        String token = UUID.randomUUID().toString();
        LocalDateTime expiry = LocalDateTime.now().plusHours(24);
        VerificationToken vt = new VerificationToken(token, user, expiry);
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

    public List<User> findAll() {
        return userRepository.findAll();
    }

    // Get User By Id
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Update User
    public void save(User user) {
        userRepository.save(user);
    }

    // Delete User
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    // Password reset flow
    public void createPasswordResetToken(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("No user with the email provided");
        }

        passwordResetTokenRepository.deleteByUserId(user.getId());
        String token = UUID.randomUUID().toString();
        PasswordResetToken prt = new PasswordResetToken(token, user,
                LocalDateTime.now().plusHours(2));
        passwordResetTokenRepository.save(prt);
        String link = appBaseUrl + "/reset-password?token=" + token;
        String subject = "Password reset request";
        String body = "Hi " + (user.getEmail() == null ? "" : user.getEmail()) + "\n\n"
                + "Click the link to reset your password: \n" + link + "\n\n"
                + "This link expires in 2 hours.\n\nIf you did not request this, ignore this email.";
        emailService.sendSimpleMessage(user.getEmail(), subject, body);
    }

    public String validatePasswordResetToken(String token) {
        var opt = passwordResetTokenRepository.findByToken(token);
        if (opt.isEmpty())
            return "invalid";
        var prt = opt.get();
        if (prt.getExpiryDate().isBefore(LocalDateTime.now()))
            return "expired";
        return "valid";
    }

    public Optional<User> getUserByPasswordResetToken(String token) {
        return passwordResetTokenRepository.findByToken(token).map(PasswordResetToken::getUser);
    }

    @Transactional
    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        passwordResetTokenRepository.deleteByUserId(user.getId());
    }

    // check logged user
    public void redirectUser(Authentication auth, Model model) {

        if (auth == null || !auth.isAuthenticated()) {
            model.addAttribute("error", "user not authenticated");
        } else {
            CustomUserDetails u = (CustomUserDetails) auth.getPrincipal();

            // Add user info to model (for Thymeleaf dashboard pages)
            model.addAttribute("id", u.getId());
            model.addAttribute("username", u.getUsername());
            model.addAttribute("password", u.getPassword());
            model.addAttribute("roles", u.getRoles());

            Profile profile = profileService.findByUserId(u.getId());
            profileService.checkProfile(u.getId(), model);
            model.addAttribute("logged", profile);
        }
    }
}
