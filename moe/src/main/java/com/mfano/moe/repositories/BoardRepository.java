package com.mfano.moe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mfano.moe.models.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    /*
     * @Query(value = "SELECT b FROM Board b" +
     * " where b.profileid" +
     * " = %?1%")
     */
    Board findByProfileId(Long pid);

    /*
     * @Query(value = "SELECT b FROM Board b" +
     * " where b.profileid" +
     * " = %?1%")
     */
    Board findByInstitutionId(Long inst);

}
