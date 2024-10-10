package com.mfano.mfano.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mfano.mfano.models.State;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {

}
