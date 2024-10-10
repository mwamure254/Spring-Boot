package com.mfano.mfano.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfano.mfano.models.Country;
import com.mfano.mfano.repositories.CountryRepository;

@Service
public class CountryService {
	@Autowired
	private CountryRepository countryRepository;

	// Get All Countries
	public List<Country> allCountries() {
		return countryRepository.findAll();
	}

	// Get Country By Id
	public Optional<Country> findById(Integer id) {
		return countryRepository.findById(id);
	}

	// Delete Country
	public void delete(Integer id) {
		countryRepository.deleteById(id);
	}

	// Update Country
	public void save(Country country) {
		countryRepository.save(country);
	}

}
