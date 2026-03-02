package com.mfano.moe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mfano.moe.models.ICategory;

@Repository
public interface ICategoryRepository extends JpaRepository<ICategory, Long> {

}
