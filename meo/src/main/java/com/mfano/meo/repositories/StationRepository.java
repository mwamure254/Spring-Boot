package com.mfano.meo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mfano.meo.models.Station;

@Repository
public interface StationRepository extends JpaRepository<Station, Long>{

}
