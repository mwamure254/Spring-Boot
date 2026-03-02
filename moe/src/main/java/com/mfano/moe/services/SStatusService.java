package com.mfano.moe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfano.moe.models.SStatus;
import com.mfano.moe.repositories.SStatusRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SStatusService {
    @Autowired
    private final SStatusRepository sStatusRepository;

    // Get All IStatuss
    public List<SStatus> findAll() {
        return sStatusRepository.findAll();
    }

    // Get IStatus By Id
    public SStatus findById(Long id) {
        return sStatusRepository.findById(id).orElse(null);
    }

    // Delete IStatus
    public void delete(Long id) {
        sStatusRepository.deleteById(id);
    }

    // Update IStatus
    public void save(SStatus IStatus) {
        sStatusRepository.save(IStatus);
    }
}
