package com.mfano.blog.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mfano.blog.models.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findBySlug(String slug);
    List<Post> findTop6ByOrderByCreatedDesc();
    List<Post> findByCategoryId(Long categoryId);

    //List<Post> findLatest(int limit);
    List<Post> findTopNByOrderByCreatedDesc(int n);
        List<Post> findByUserid(Long userid);
    List<Post> findAll();

     //Page<Post> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
    //Page<Post> findAll(Pageable pageable);
}
