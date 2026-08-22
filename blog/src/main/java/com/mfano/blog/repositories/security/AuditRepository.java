package com.mfano.blog.repositories.security;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfano.blog.models.security.AuditEntry;

public interface AuditRepository extends JpaRepository<AuditEntry, Long> {

}
