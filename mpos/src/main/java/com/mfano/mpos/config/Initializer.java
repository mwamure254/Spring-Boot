package com.mfano.mpos.config;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.mfano.mpos.models.Branch;
import com.mfano.mpos.models.security.Role;
import com.mfano.mpos.models.security.User;
import com.mfano.mpos.repositories.BranchRepository;
import com.mfano.mpos.repositories.security.RoleRepository;
import com.mfano.mpos.repositories.security.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Initializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final BranchRepository branchRepo;

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        // initialize roles
        List<String> defaultRoles = List.of("ADMIN", "MANAGER", "CASHIER", "PROCUREMENT");

        for (String r : defaultRoles) {
            if (roleRepo.findByName(r).isEmpty()) {
                Role role = new Role();
                role.setName(r);
                role.setCreatedBy("sys");
                roleRepo.save(role);

            }
        }
        // initialize branches
        List<String> defaultBranches = List.of("HQ", "OTHER");

        for (String r : defaultBranches) {
            if (roleRepo.findByName(r).isEmpty()) {
                Branch branch = new Branch();
                branch.setName(r);
                branch.setCreatedBy("sys");
                branchRepo.save(branch);

            }
        }
        // initialize admin
        if (userRepository.findByEmail(adminEmail) == null) {
            User admin = new User();

            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setEnabled(true);
            admin.setBranch(branchRepo.findByName("HQ"));
            admin.setCreatedBy("sys");
            admin.setRoles(Set.of(roleRepo.findByName("ADMIN").get()));

            userRepository.save(admin);
        }
    }

}
