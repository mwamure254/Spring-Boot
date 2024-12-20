package com.mfano.mfano.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfano.mfano.models.Country;
import com.mfano.mfano.services.CountryService;

@Controller
public class CountryController {
	@Autowired
	private CountryService countryService;

	// Get All Countries
	@GetMapping("/countries")
	public String getCountries(Model model) {
		model.addAttribute("countries", countryService.allCountries());
		return "country";
	}

	@GetMapping("/countries/findById/")
	@ResponseBody
	public Optional<Country> findById(Integer id) {
		return countryService.findById(id);
	}

	// Add Country
	@PostMapping(value = "/countries/addNew")
	public String addNew(Country country) {
		countryService.save(country);
		return "redirect:/countries";
	}

	@RequestMapping(value = "/countries/update", method = { RequestMethod.PUT, RequestMethod.GET })
	public String update(Country country) {
		countryService.save(country);
		return "redirect:/countries";
	}

	@RequestMapping(value = "/countries/delete/", method = { RequestMethod.DELETE, RequestMethod.GET })
	public String delete(Integer id) {
		countryService.delete(id);
		return "redirect:/countries";
	}

}
