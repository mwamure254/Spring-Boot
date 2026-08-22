package com.mfano.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfano.blog.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
}
