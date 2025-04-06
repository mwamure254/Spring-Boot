package com.example.MfanoFleet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.MfanoFleet.services.CountryService;

@Controller
public class CountryController {
	@Autowired
	private CountryService countryService;
	
	@GetMapping("countries")
	public String showCountries(Model model) {
		model.addAttribute("countries", countryService.getCountries());
		return "country";
	}

}
