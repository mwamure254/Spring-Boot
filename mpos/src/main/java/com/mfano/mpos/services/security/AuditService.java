package com.mfano.mpos.services.security;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mfano.mpos.models.security.AuditEntry;
import com.mfano.mpos.repositories.security.AuditRepository;

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

    public List<AuditEntry> findByAction(String action) {
        return repo.findByAction(action);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    public void deleteAll() {
        repo.deleteAll();
    }

    public AuditEntry findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void updateAuditEntry(Long id, AuditEntry updatedEntry) {
        AuditEntry existingEntry = repo.findById(id).orElse(null);
        if (existingEntry != null) {
            existingEntry.setAction(updatedEntry.getAction());
            existingEntry.setPerformedBy(updatedEntry.getPerformedBy());
            existingEntry.setDetails(updatedEntry.getDetails());
            repo.save(existingEntry);
        }
    }

    public void createAuditEntry(AuditEntry entry) {
        repo.save(entry);
    }

    public List<AuditEntry> findByPerformedBy(String performedBy) {
        return repo.findAll().stream()
                .filter(entry -> entry.getPerformedBy().equals(performedBy))
                .toList();
    }

    public List<AuditEntry> findByDetailsContaining(String keyword) {
        return repo.findAll().stream()
                .filter(entry -> entry.getDetails() != null && entry.getDetails().contains(keyword))
                .toList();
    }

    public List<AuditEntry> findByActionAndPerformedBy(String action, String performedBy) {
        return repo.findAll().stream()
                .filter(entry -> entry.getAction().equals(action) && entry.getPerformedBy().equals(performedBy))
                .toList();
    }

}
