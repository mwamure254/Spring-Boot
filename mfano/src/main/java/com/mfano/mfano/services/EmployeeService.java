package com.mfano.mfano.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfano.mfano.models.Employee;
import com.mfano.mfano.models.User;
import com.mfano.mfano.repositories.EmployeeRepository;
import com.mfano.mfano.repositories.UserRepository;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private UserRepository userRepository;

	// Get All Employees
	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}

	// Get Employee By Id
	public Optional<Employee> findById(int id) {
		return employeeRepository.findById(id);
	}

	// Delete Employee
	public void delete(int id) {
		employeeRepository.deleteById(id);
	}

	// Update Employee
	public void save(Employee employee) {
		employeeRepository.save(employee);
	}

	public Employee findByUsername(String un) {
		return employeeRepository.findByUsername(un);
	}

	// Set the UserName of the employee where FirstName and LastName match
	public void assignUsername(int id) {
		Employee employee = employeeRepository.findById(id).orElse(null);

		User user = userRepository.findByFirstnameAndLastname(employee.getFirstname(), employee.getLastname());
		System.out.println(user);
		employee.setUsername(user.getUsername());
		employeeRepository.save(employee);
	}
}
