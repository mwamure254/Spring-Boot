package com.mfano.meo.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mfano.meo.models.Profile;
import com.mfano.meo.services.ProfileService;
import com.mfano.meo.services.UserService;

@RestController
public class ProfileController {
    @Autowired
    private ProfileService ps; 
	@Autowired
    private UserService us;

    // Get All profiles
	@GetMapping("p")
	public String findAll(Model model) {
		model.addAttribute("p", ps.findAll());
		model.addAttribute("user", us.findAll());

		return "p";
	}

	@GetMapping("p/findById/")
	@ResponseBody
	public Optional<Profile> findById(Long id) {
		return ps.findById(id);
	}

    // Add profile
	@PostMapping(value = "p/add")
	public String addNew(Profile profile) {
		ps.save(profile);

		return "redirect:/p";
	}

	//Update profile
	@RequestMapping(value = "p/update", method = { RequestMethod.PUT, RequestMethod.GET })
	public String update(Profile profile) {
		ps.save(profile);
		return "redirect:/p";
	}

	//Delete profile
	@RequestMapping(value = "p/delete/", method = { RequestMethod.DELETE, RequestMethod.GET })
	public String delete(Long id) {
		ps.delete(id);

		return "redirect:/p";
	}
}
