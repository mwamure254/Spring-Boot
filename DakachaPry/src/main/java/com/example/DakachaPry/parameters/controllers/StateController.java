package com.example.DakachaPry.parameters.controllers;

import java.util.List;

import com.example.DakachaPry.parameters.models.State;
import com.example.DakachaPry.parameters.services.CountryService;
import com.example.DakachaPry.parameters.services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StateController {

	@Autowired
	private StateService stateService;
	@Autowired
	private CountryService countryService;

	public Model addModelAttribute(Model model) {
		model.addAttribute("states", stateService.findAll());
		model.addAttribute("countries", countryService.findAll());
		return model;
	}

	// Get All States
	@GetMapping("/parameters/states")
	public String findAll(Model model) {
		addModelAttribute(model);
		return "/parameters/states";
	}

	// Get States by country id
	@GetMapping("/parameters/states/country/{countryid}")
	@ResponseBody
	public List<State> findAll(@PathVariable Integer countryid) {
		return stateService.findByCountryid(countryid);
	}

	@GetMapping("/parameters/stateAdd")
	public String addState(Model model) {
		addModelAttribute(model);
		return "parameters/stateAdd";
	}

	@GetMapping("/parameters/state/{op}/{id}")
	public String editState(@PathVariable Integer id, @PathVariable String op, Model model) {
		addModelAttribute(model);
		model.addAttribute("state", stateService.findById(id));
		return "/parameters/state" + op;
	}

	// Add State
	@PostMapping(value = "/parameters/states")
	public String addNew(State state) {
		stateService.save(state);
		return "redirect:/parameters/states";
	}

	@RequestMapping(value = "/parameters/states/delete/{id}", method = { RequestMethod.DELETE, RequestMethod.GET })
	public String delete(@PathVariable Integer id) {
		stateService.delete(id);
		return "redirect:/parameters/states";
	}
}
