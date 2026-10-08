package com.mfano.zuru.auth.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.mfano.zuru.auth.models.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    Branch findByName(String name);
}
