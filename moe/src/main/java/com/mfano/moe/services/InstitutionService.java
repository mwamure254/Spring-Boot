package com.mfano.moe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfano.moe.models.Institution;
import com.mfano.moe.repositories.InstitutionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InstitutionService {
    @Autowired
    private final InstitutionRepository institutionRepository;

    // Get All ILevels
    public List<Institution> findAll() {
        return institutionRepository.findAll();
    }

    // Get ILevel By Id
    public Institution findById(Long id) {
        return institutionRepository.findById(id).orElse(null);
    }

    // Delete ILevel
    public void delete(Long id) {
        institutionRepository.deleteById(id);
    }

    // Update ILevel
    public void save(Institution school) {
        institutionRepository.save(school);
    }

}
