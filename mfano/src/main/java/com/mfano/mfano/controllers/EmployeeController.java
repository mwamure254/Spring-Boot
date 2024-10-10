package com.mfano.mfano.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mfano.mfano.models.Employee;
import com.mfano.mfano.services.CountryService;
import com.mfano.mfano.services.EmployeeService;
import com.mfano.mfano.services.EmployeeTypeService;
import com.mfano.mfano.services.JobTitleService;
import com.mfano.mfano.services.StateService;

@Controller
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private StateService stateService;
	@Autowired
	private JobTitleService jobTitleService;
	@Autowired
	private EmployeeTypeService employeeTypeService;
	@Autowired
	private CountryService countryService;

	private static String routD = "redirect:/employees";
	private static String baseDirectory = "C:\\Users\\mwamu\\git\\repository\\DakachaPry\\src\\main\\resources\\static\\img\\uploads\\employees\\";

	// Get All Employees
	@GetMapping("employees")
	public String findAll(Model model) {
		model.addAttribute("countries", countryService.allCountries());
		model.addAttribute("states", stateService.findAll());
		model.addAttribute("employees", employeeService.findAll());
		model.addAttribute("jobTitles", jobTitleService.findAll());
		model.addAttribute("employeeTypes", employeeTypeService.findAll());

		return "employee";
	}

	@GetMapping("employees/findById/")
	@ResponseBody
	public Optional<Employee> findById(Integer id) {
		return employeeService.findById(id);
	}

	// Add Employee
	@PostMapping(value = "employees/addNew")
	public String addNew(Employee employee){
		
		employeeService.save(employee);

		return routD;
	}

	@RequestMapping(value = "/employees/update", method = { RequestMethod.PUT, RequestMethod.GET })
	public String update(Employee employee) {
		employeeService.save(employee);

		return routD;
	}

	@RequestMapping(value = "/employees/delete/", method = { RequestMethod.DELETE, RequestMethod.GET })
	public String delete(Integer id) {
		employeeService.delete(id);

		return routD;
	}

	// Assign Employee UserName
	@RequestMapping(value = "/employees/assignUsername/", method = { RequestMethod.PUT, RequestMethod.GET })
	public String assignUsername(int id) {
		employeeService.assignUsername(id);

		return routD;
	}

	@PostMapping(value = "/employees/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> uploadFile(@RequestParam("photo") MultipartFile file)
			throws IOException {
		File newFile = new File(baseDirectory + file.getOriginalFilename());
		newFile.createNewFile();
		FileOutputStream fout = new FileOutputStream(newFile);
		fout.write(file.getBytes());
		fout.close();
		return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
	}

	@PostMapping("/employees/uploadFile2")
	public String uploadFile2(@RequestParam("photo") MultipartFile file)
			throws IllegalStateException, IOException {

		file.transferTo(new File(baseDirectory + file.getOriginalFilename()));
		return routD;
	}
}
