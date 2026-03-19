package com.mfano.moe.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mfano.moe.models.ILevel;
import com.mfano.moe.repositories.ILevelRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ILevelService {
    private final ILevelRepository iLevelRepository;

    // Get All ILevels
    public List<ILevel> findAll() {
        return iLevelRepository.findAll();
    }

    // Get ILevel By Id
    public ILevel findById(Long id) {
        return iLevelRepository.findById(id).orElse(null);
    }

    // Delete ILevel
    public void delete(Long id) {
        iLevelRepository.deleteById(id);
    }

    // Update ILevel
    public void save(ILevel ILevel) {
        iLevelRepository.save(ILevel);
    }
}
