package com.mfano.mpos.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfano.mpos.models.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    Branch findByName(String name);

    Optional<Branch> findByCode(String code);

    boolean existsByNameIgnoreCase(String name);

    boolean existsByCodeIgnoreCase(String code);
}
