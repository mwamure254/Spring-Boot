package com.mfano.moe.security.service;

import com.mfano.moe.security.model.AuditEntry;
import com.mfano.moe.security.repository.AuditEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditService {
    private final AuditEntryRepository repo;

    public void record(String action, String performedBy, String details) {
        repo.save(new AuditEntry(action, performedBy, details));
    }

    public List<AuditEntry> findAll() {
        return repo.findAll();
    }
}
