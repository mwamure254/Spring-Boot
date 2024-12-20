package com.example.DakachaPry.security.controllers;

import com.example.DakachaPry.security.models.User;
import com.example.DakachaPry.security.services.RoleService;
import com.example.DakachaPry.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	@GetMapping("/security/users")
	public String getUser(Model model) {
		model.addAttribute("users", userService.findAll());
		return "/security/users";
	}
	
	@RequestMapping(value = "/security/user/delete/{id}", method = { RequestMethod.GET, RequestMethod.DELETE })
	public String delete(@PathVariable Integer id) {
		userService.delete(id);
		return "redirect:/security/users";
	}

	// The op parameter is either Edit or Details
	@GetMapping("/security/user/{op}/{id}")
	public String editEmployee(@PathVariable Integer id, @PathVariable String op, Model model) {
		User user = userService.findById(id);
		model.addAttribute("user", user);
		model.addAttribute("userRoles", roleService.getUserRoles(user));
		model.addAttribute("userNotRoles", roleService.getUserNotRoles(user));
		return "/security/user" + op; // returns employeeEdit or employeeDetails
	}

	// Modified method to Add a new user User
	@PostMapping(value = "users/addNew")
	public RedirectView addNew(User user, RedirectAttributes redir) {

		userService.save(user);

		RedirectView redirectView = new RedirectView("/login", true);

		redir.addFlashAttribute("message", "You successfully registered! You can now login");

		return redirectView;
	}

}
