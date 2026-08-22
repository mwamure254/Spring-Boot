package com.mfano.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfano.blog.models.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
    
}
