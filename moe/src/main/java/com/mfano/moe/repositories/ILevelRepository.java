package com.mfano.moe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mfano.moe.models.ILevel;

@Repository
public interface ILevelRepository extends JpaRepository<ILevel, Long> {

}
