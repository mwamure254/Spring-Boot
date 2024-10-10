package com.mfano.mfano.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mfano.mfano.models.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

}
