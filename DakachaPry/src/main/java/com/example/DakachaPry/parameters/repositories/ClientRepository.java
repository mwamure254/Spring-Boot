package com.example.DakachaPry.parameters.repositories;

import com.example.DakachaPry.parameters.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

}
