package com.mfano.mfano.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfano.mfano.models.Location;
import com.mfano.mfano.services.CountryService;
import com.mfano.mfano.services.LocationService;
import com.mfano.mfano.services.StateService;

@Controller
public class LocationController {
	@Autowired
	private LocationService locationService;
	@Autowired
	private CountryService countryService;
	@Autowired
	private StateService stateService;

	@GetMapping("locations")
	public String findAll(Model model) {
		model.addAttribute("locations", locationService.findAll());
		model.addAttribute("countries", countryService.allCountries());
		model.addAttribute("states", stateService.findAll());
		return "location";
	}

	@GetMapping("locations/findById/")
	@ResponseBody
	public Optional<Location> findById(Integer id) {
		return locationService.findById(id);
	}

	@GetMapping("locations/findByDescriptionContaining/{description}")
	public List<Location> findByDescriptionContaining(@PathVariable String description) {
		return locationService.findByDescriptionContaining(description);
	}

	@PostMapping("locations/addNew")
	public String save(Location location) {
		locationService.save(location);
		return "redirect:/locations";
	}

	@RequestMapping(value = "locations/update", method = { RequestMethod.PUT, RequestMethod.GET })
	public String update(Location location) {
		locationService.save(location);
		return "redirect:/locations";
	}

	@RequestMapping(value = "locations/delete/", method = { RequestMethod.DELETE, RequestMethod.GET })
	public String deleteById(Integer id) {
		locationService.deleteById(id);
		return "redirect:/locations";
	}
}
