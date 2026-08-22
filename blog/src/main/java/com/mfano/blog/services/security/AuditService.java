package com.mfano.blog.services.security;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mfano.blog.models.security.AuditEntry;
import com.mfano.blog.repositories.security.AuditRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuditService {
    private final AuditRepository repo;

    public void record(String action, String performedBy, String details) {
        AuditEntry entry = new AuditEntry();
        entry.setAction(action);
        entry.setPerformedBy(performedBy);
        entry.setDetails(details);
        repo.save(entry);
    }

    public List<AuditEntry> findAll() {
        return repo.findAll();
    }
}
