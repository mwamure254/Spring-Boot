package com.mfano.moe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mfano.moe.models.Institution;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {

}
