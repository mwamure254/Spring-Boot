package com.mfano.mpos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfano.mpos.models.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    
}
