package com.mfano.meo.security;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
	@Autowired
	private RoleRepository RoleRepository;
	// Get All Roles
	public List<Role> findAll() {
		return RoleRepository.findAll();
	}

	// Get Role By Id
	public Optional<Role> findById(Long id) {
		return RoleRepository.findById(id);
	}

	// Delete Role
	public void delete(Long id) {
		RoleRepository.deleteById(id);
	}

	// Update Role
	public void save(Role Role) {
		RoleRepository.save(Role);
	}

}
