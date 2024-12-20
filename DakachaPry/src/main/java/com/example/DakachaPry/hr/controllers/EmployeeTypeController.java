package com.example.DakachaPry.hr.controllers;

import java.util.List;

import com.example.DakachaPry.hr.models.EmployeeType;
import com.example.DakachaPry.hr.services.EmployeeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmployeeTypeController {

	@Autowired
	private EmployeeTypeService employeeTypeService;

	@GetMapping("/hr/employeeTypes")
	public String parameters(Model model) {
		List<EmployeeType> employeeTypes = employeeTypeService.findAll();
		model.addAttribute("employeeTypes", employeeTypes);
		return "hr/employeeTypes";
	}

	// Get Job Title by id
	@GetMapping("/hr/employeeType/{id}")
	@ResponseBody
	public EmployeeType getById(@PathVariable Integer id) {
		return employeeTypeService.findById(id).orElse(null);
	}

	@PostMapping("/hr/employeeTypes")
	public String save(EmployeeType employeeType) {
		employeeTypeService.save(employeeType);
		return "redirect:/hr/employeeTypes";
	}

	@RequestMapping(value = "/hr/employeeType/delete/{id}", method = { RequestMethod.DELETE, RequestMethod.GET })
	public String delete(@PathVariable Integer id) {
		employeeTypeService.delete(id);
		return "redirect:/hr/employeeTypes";
	}

}
