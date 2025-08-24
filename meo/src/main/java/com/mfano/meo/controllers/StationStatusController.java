package com.mfano.meo.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfano.meo.models.StationStatus;
import com.mfano.meo.services.StationStatusService;

public class StationStatusController {
    @Autowired
    private StationStatusService sss;

    // Get All ss
	@GetMapping("/ss")
	public String findAll(Model model) {
		model.addAttribute("ss", sss.findAll());
		return "ss";
	}

	@GetMapping("ss/findById/")
	@ResponseBody
	public Optional<StationStatus> findById(Long id) {
		return sss.findById(id);
	}

    // Add ss
	@PostMapping(value = "ss/add")
	public String addNew(StationStatus stationStatus) {
		sss.save(stationStatus);
		return "redirect:/ss";
	}

	//Update ss
	@RequestMapping(value = "ss/update", method = { RequestMethod.PUT, RequestMethod.GET })
	public String update(StationStatus stationStatus) {
		sss.save(stationStatus);
		return "redirect:/ss";
	}

	//Delete ss
	@RequestMapping(value = "ss/delete/", method = { RequestMethod.DELETE, RequestMethod.GET })
	public String delete(Long id) {
		sss.delete(id);
		return "redirect:/ss";
	}

}
