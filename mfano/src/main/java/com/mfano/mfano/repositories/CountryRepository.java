package com.mfano.mfano.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mfano.mfano.models.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

}
