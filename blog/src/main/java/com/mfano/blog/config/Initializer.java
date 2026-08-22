package com.mfano.blog.config;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.mfano.blog.models.security.Role;
import com.mfano.blog.models.security.User;
import com.mfano.blog.repositories.security.RoleRepository;
import com.mfano.blog.repositories.security.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Initializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        List<String> defaultRoles = List.of("ADMIN", "AUTHOR", "EDITOR", "USER");

        for (String r : defaultRoles) {
            if (roleRepo.findByName(r).isEmpty()) {
                Role role = new Role();
                role.setName(r);
                roleRepo.save(role);

            }
        }

        if (userRepository.findByEmail(adminEmail) == null) {
            User admin = new User();

            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setEnabled(true);
            admin.setRoles(Set.of(roleRepo.findByName("ADMIN").get()));

            userRepository.save(admin);
        }
    }

}
