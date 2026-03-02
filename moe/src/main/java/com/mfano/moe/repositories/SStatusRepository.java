package com.mfano.moe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mfano.moe.models.SStatus;

@Repository
public interface SStatusRepository extends JpaRepository<SStatus, Long> {

}
