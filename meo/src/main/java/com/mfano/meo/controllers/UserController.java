package com.mfano.meo.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfano.meo.models.User;
import com.mfano.meo.services.UserService;

public class UserController {
    @Autowired
    private UserService us;

    // Get All u
	@GetMapping("/u")
	public String findAll(Model model) {
		model.addAttribute("u", us.findAll());
		return "u";
	}

	@GetMapping("u/findById/")
	@ResponseBody
	public Optional<User> findById(Long id) {
		return us.findById(id);
	}

    // Add u
	@PostMapping(value = "u/add")
	public String addNew(User user) {
		us.save(user);
		return "redirect:/u";
	}

	//Update ss
	@RequestMapping(value = "u/update", method = { RequestMethod.PUT, RequestMethod.GET })
	public String update(User user) {
		us.save(user);
		return "redirect:/u";
	}

	//Delete ss
	@RequestMapping(value = "u/delete/", method = { RequestMethod.DELETE, RequestMethod.GET })
	public String delete(Long id) {
		us.delete(id);
		return "redirect:/u";
	}

}
