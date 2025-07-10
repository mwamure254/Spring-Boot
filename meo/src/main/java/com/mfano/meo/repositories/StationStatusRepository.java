package com.mfano.meo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mfano.meo.models.StationStatus;

@Repository
public interface StationStatusRepository extends JpaRepository<StationStatus, Long>{

}
