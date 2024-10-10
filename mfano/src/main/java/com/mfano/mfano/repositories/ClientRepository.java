package com.mfano.mfano.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mfano.mfano.models.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

}
