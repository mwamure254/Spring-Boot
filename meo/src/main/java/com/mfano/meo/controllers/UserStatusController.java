package com.mfano.meo.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfano.meo.models.UserStatus;
import com.mfano.meo.services.UserStatusService;

@Controller
public class UserStatusController {
    @Autowired
    private UserStatusService uss;

    // Get All ss
	@GetMapping("us")
	public String findAll(Model model) {
		model.addAttribute("us", uss.findAll());
		return "us";
	}

	@GetMapping("us/findById/")
	@ResponseBody
	public Optional<UserStatus> findById(Integer id) {
		return uss.findById(id);
	}

    // Add ss
	@PostMapping(value = "us/newStatus")
	public String addNew(UserStatus userStatus) {
		uss.save(userStatus);
		return "redirect:/us";
	}

	//Update us
	@RequestMapping(value = "us/update", method = { RequestMethod.PUT, RequestMethod.GET })
	public String update(UserStatus userStatus) {
		uss.save(userStatus);
		return "redirect:/us";
	}

	//Delete us
	@RequestMapping(value = "us/delete/", method = { RequestMethod.DELETE, RequestMethod.GET })
	public String delete(Integer id) {
		uss.delete(id);
		return "redirect:/us";
	}
    
}
