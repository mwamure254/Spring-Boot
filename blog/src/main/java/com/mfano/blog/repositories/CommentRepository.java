package com.mfano.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfano.blog.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    
}
