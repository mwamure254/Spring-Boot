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

import com.mfano.meo.services.ServiceStatusService;
import com.mfano.meo.models.ServiceStatus;

@RestController
public class ServiceStatusController {
    @Autowired
    private ServiceStatusService sss;

    // Get All ss
	@GetMapping("services")
	public String findAll(Model model) {
		model.addAttribute("services", sss.findAll());
		return "services";
	}

	@GetMapping("services/findById/")
	@ResponseBody
	public Optional<ServiceStatus> findById(Integer id) {
		return sss.findById(id);
	}

    // Add ss
	@PostMapping(value = "services/add")
	public String addNew(ServiceStatus serviceStatus) {
		sss.save(serviceStatus);
		return "redirect:/services";
	}

	//Update ss
	@RequestMapping(value = "services/update", method = { RequestMethod.PUT, RequestMethod.GET })
	public String update(ServiceStatus serviceStatus) {
		sss.save(serviceStatus);
		return "redirect:/services";
	}

	//Delete ss
	@RequestMapping(value = "services/delete/", method = { RequestMethod.DELETE, RequestMethod.GET })
	public String delete(Integer id) {
		sss.delete(id);
		return "redirect:/services";
	}
}
