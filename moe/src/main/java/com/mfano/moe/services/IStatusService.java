package com.mfano.moe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfano.moe.models.IStatus;
import com.mfano.moe.repositories.IStatusRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IStatusService {
    @Autowired
    private final IStatusRepository iStatusRepository;

    // Get All IStatuss
    public List<IStatus> findAll() {
        return iStatusRepository.findAll();
    }

    // Get IStatus By Id
    public IStatus findById(Long id) {
        return iStatusRepository.findById(id).orElse(null);
    }

    // Delete IStatus
    public void delete(Long id) {
        iStatusRepository.deleteById(id);
    }

    // Update IStatus
    public void save(IStatus IStatus) {
        iStatusRepository.save(IStatus);
    }
}
