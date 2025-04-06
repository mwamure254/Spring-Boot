package com.example.MfanoFleet.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MfanoFleet.models.Country;
import com.example.MfanoFleet.repositories.CountryRepository;

@Service
public class CountryService {
	
	@Autowired
	private CountryRepository countryRepository;
	
	//Get All Countries
	public List<Country> getCountries(){
		return countryRepository.findAll();
	}

}
