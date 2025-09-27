package com.mfano.meo.security;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
	@Autowired
	private RoleRepository roleRepository;
	// Get All Roles
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	// Get Role By Id
	public Optional<Role> findById(Long id) {
		return roleRepository.findById(id);
	}

	// Delete Role
	public void delete(Long id) {
		roleRepository.deleteById(id);
	}

	// Update Role
	public void save(Role role) {
		roleRepository.save(role);
	}

}
