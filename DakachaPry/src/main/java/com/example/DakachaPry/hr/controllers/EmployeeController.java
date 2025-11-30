package com.example.DakachaPry.hr.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.DakachaPry.hr.models.Employee;
import com.example.DakachaPry.hr.services.EmployeeService;
import com.example.DakachaPry.hr.services.EmployeeTypeService;
import com.example.DakachaPry.hr.services.JobTitleService;
import com.example.DakachaPry.parameters.services.CountryService;
import com.example.DakachaPry.parameters.services.StateService;

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
	
	private final String baseDirectory = "src/main/resources/static/img/uploads/employees/";

	public Model addModelAttributes(Model model) {
		model.addAttribute("countries", countryService.findAll());
		model.addAttribute("states", stateService.findAll());
		model.addAttribute("employees", employeeService.findAll());
		model.addAttribute("jobTitles", jobTitleService.findAll());
		model.addAttribute("employeeTypes", employeeTypeService.findAll());
		return model;
	}

	// Get All Employees
	@GetMapping("hr/employees")
	public String findAll(Model model) {
		addModelAttributes(model);
		return "/hr/employees";
	}

	@GetMapping("/hr/employeeAdd")
	public String addEmployee(Model model) {
		addModelAttributes(model);
		return "/hr/employeeAdd";
	}

	// The op parameter is either Edit or Details
	@GetMapping("/hr/employee/{op}/{id}")
	public String editEmployee(@PathVariable Integer id, @PathVariable String op, Model model) {
		Employee employee = employeeService.findById(id);
		model.addAttribute("employee", employee);
		addModelAttributes(model);
		return "/hr/employee" + op; // returns employeeEdit or employeeDetails
	}

	// Add Employee (value="yyyy-mm-dd")
	@PostMapping("/hr/employees")
	public String addNew(
			@RequestParam String title, @RequestParam String initials
			, @RequestParam("socialSecurityNumber") String ssn, @RequestParam String firstname
			, @RequestParam String lastname, @RequestParam String othername
			, @RequestParam String gender, @RequestParam int countryid
			, @RequestParam String address, @RequestParam int employeetypeid
			, @RequestParam("dateOfBirth") String dob, @RequestParam("hireDate") String hd
			, @RequestParam("stateid") int si, @RequestParam String city
			, @RequestParam String phone, @RequestParam String mobile
			, @RequestParam("maritalStatus") String ms, @RequestParam String email
			, @RequestParam("jobtitleid") int jti, @RequestParam("photo") MultipartFile file) {
		
		Employee employee=new Employee();
		
		employee.setTitle(title);
		employee.setInitials(initials);
		employee.setAddress(address);
		employee.setCity(city);
		employee.setStateid(si);
		employee.setCountryid(countryid);
		employee.setEmployeetypeid(employeetypeid);
		employee.setJobtitleid(jti);
		
		employee.setDateOfBirth(LocalDate.parse(dob));
		employee.setHireDate(LocalDate.parse(hd));
		
		employee.setEmail(email);
		employee.setFirstname(firstname);
		employee.setLastname(lastname);
		employee.setOthername(othername);
		employee.setGender(gender);
		employee.setMaritalStatus(ms);
		employee.setSocialSecurityNumber(ssn);
		employee.setPhone(phone);
		employee.setMobile(mobile);
		
		try {
			employee.setPhoto(uploadFile(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		employeeService.save(employee);
		return "redirect:/hr/employees";
	}

	@RequestMapping(value = "/hr/employee/delete/{id}", method = { RequestMethod.DELETE, RequestMethod.GET })
	public String delete(@PathVariable Integer id) {
		employeeService.delete(id);
		return "redirect:/hr/employees";
	}

	//@PostMapping("/employees/uploadPhoto")
	public String uploadFile(MultipartFile file) throws IOException {
	
		String filename = file.getOriginalFilename();
		Path path = Path.of(baseDirectory + filename);
		Files.createDirectories(path.getParent());
		Files.write(path, file.getBytes());
		
		return filename;
	}
}
