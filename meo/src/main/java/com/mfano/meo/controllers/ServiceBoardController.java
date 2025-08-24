package com.mfano.meo.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfano.meo.models.ServiceBoard;
import com.mfano.meo.services.ProfileService;
import com.mfano.meo.services.ServiceBoardService;
import com.mfano.meo.services.ServiceStatusService;
import com.mfano.meo.services.StationService;
import com.mfano.meo.services.StationStatusService;

public class ServiceBoardController {
    @Autowired
    private ServiceBoardService sbs;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private StationService stationService;
    @Autowired
    private StationStatusService stationStatusService;
    @Autowired
    private ServiceStatusService serviceStatusService;

    //Get All sb
	@GetMapping("/sb")
	public String findAll(Model model) {
		model.addAttribute("sb", sbs.findAll());
		model.addAttribute("profile", profileService.findAll());
		model.addAttribute("station", stationService.findAll());
		model.addAttribute("stationStatus", stationStatusService.findAll());
		model.addAttribute("serviceStatus", serviceStatusService.findAll());

		return "sb";
	}

	@GetMapping("sb/findById/")
	@ResponseBody
	public Optional<ServiceBoard> findById(Long id) {
		return sbs.findById(id);
	}

    //Add sb
	@PostMapping(value = "sb/add")
	public String addNew(ServiceBoard serviceBoard) {
		sbs.save(serviceBoard);
		return "redirect:/sb";
	}

	//Update sb
	@RequestMapping(value = "sb/update", method = { RequestMethod.PUT, RequestMethod.GET })
	public String update(ServiceBoard serviceBoard) {
		sbs.save(serviceBoard);
		return "redirect:/sb";
	}

	//Delete sb
	@RequestMapping(value = "sb/delete/", method = { RequestMethod.DELETE, RequestMethod.GET })
	public String delete(Long id) {
		sbs.delete(id);
		return "redirect:/sb";
	}
}
