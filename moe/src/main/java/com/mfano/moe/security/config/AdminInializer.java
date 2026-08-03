package com.mfano.moe.security.config;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.mfano.moe.security.model.Role;
import com.mfano.moe.security.model.User;
import com.mfano.moe.security.repository.RoleRepository;
import com.mfano.moe.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminInializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        List<String> defaultRoles = List.of("ADMIN", "DIRECTOR", "HOI", "USER");

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
