package com.mfano.moe.security.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfano.moe.security.model.Role;
import com.mfano.moe.security.model.User;
import com.mfano.moe.security.repository.RoleRepository;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    // Get All Roles
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    // Get Role By Id
    public Role findById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    // Delete Role
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }

    // Update Role
    public void save(Role role) {
        roleRepository.save(role);
    }

    public List<Role> getUserNotRoles(User user) {
        return roleRepository.getUserNotRoles(user.getId());
    }

    public Set<Role> getUserRoles(User user) {
        return user.getRoles();
    }

    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }
}
