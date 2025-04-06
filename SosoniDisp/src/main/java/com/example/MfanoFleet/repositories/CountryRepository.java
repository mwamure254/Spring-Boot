package com.example.MfanoFleet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MfanoFleet.models.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

}
