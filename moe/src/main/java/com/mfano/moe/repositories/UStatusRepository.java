package com.mfano.moe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mfano.moe.models.UStatus;

@Repository
public interface UStatusRepository extends JpaRepository<UStatus, Long> {

}
