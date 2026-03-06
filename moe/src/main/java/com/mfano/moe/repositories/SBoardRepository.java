package com.mfano.moe.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mfano.moe.models.SBoard;

@Repository
public interface SBoardRepository extends JpaRepository<SBoard, Long> {

    Optional<SBoard> findByInstitutionId(Long inst);

    Optional<SBoard> findByProfileId(Long pro);

}
