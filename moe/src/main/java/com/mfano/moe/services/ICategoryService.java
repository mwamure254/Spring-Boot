package com.mfano.moe.services;

import com.mfano.moe.models.ICategory;
import com.mfano.moe.repositories.ICategoryRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ICategoryService {
    private final ICategoryRepository iCategoryRepository;

    // Get All ICategorys
    public List<ICategory> findAll() {
        return iCategoryRepository.findAll();
    }

    // Get ICategory By Id
    public ICategory findById(Long id) {
        return iCategoryRepository.findById(id).orElse(null);
    }

    // Delete ICategory
    public void delete(Long id) {
        iCategoryRepository.deleteById(id);
    }

    // Update ICategory
    public void save(ICategory ICategory) {
        iCategoryRepository.save(ICategory);
    }
}
