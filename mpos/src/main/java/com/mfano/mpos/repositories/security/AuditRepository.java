package com.mfano.mpos.repositories.security;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfano.mpos.models.security.AuditEntry;

public interface AuditRepository extends JpaRepository<AuditEntry, Long> {

    List<AuditEntry> findByAction(String action);
    
}
