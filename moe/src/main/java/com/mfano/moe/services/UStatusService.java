package com.mfano.moe.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mfano.moe.models.UStatus;
import com.mfano.moe.repositories.UStatusRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UStatusService {
    private final UStatusRepository uStatusRepository;

    // Get All IStatuss
    public List<UStatus> findAll() {
        return uStatusRepository.findAll();
    }

    // Get IStatus By Id
    public UStatus findById(Long id) {
        return uStatusRepository.findById(id).orElse(null);
    }

    // Delete IStatus
    public void delete(Long id) {
        uStatusRepository.deleteById(id);
    }

    // Update IStatus
    public void save(UStatus IStatus) {
        uStatusRepository.save(IStatus);
    }
}
