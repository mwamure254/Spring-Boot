package com.mfano.blog.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mfano.blog.models.Category;
import com.mfano.blog.repositories.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
private final CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
    
}
