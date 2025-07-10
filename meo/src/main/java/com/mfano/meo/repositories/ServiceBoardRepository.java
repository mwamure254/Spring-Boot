package com.mfano.meo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mfano.meo.models.ServiceBoard;

@Repository
public interface ServiceBoardRepository extends JpaRepository<ServiceBoard, Long>{

}
