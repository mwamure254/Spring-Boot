package com.mfano.meo.hois;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

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

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class UserController1 {
	@Autowired
	private final UserService1 foodService;
	
	private final String UPLOAD_DIR = "src/main/resources/static/img/uploads/users/";
	
	@GetMapping("/users")
	public String getAllFoods(Model model) {
		model.addAttribute("users", foodService.findAll());

		return "security/blank";
	}

	@GetMapping("/users/")
	public Optional<User1> getFoodById(@PathVariable int id) {
		return foodService.findById(id);
	}

	@PostMapping("/users/add")
	public String addUser(
			@RequestParam String name,
			@RequestParam("description") String desc, 
			@RequestParam double price,
			@RequestParam MultipartFile image) {
		
		final User1 client = new User1();
		client.setDescription(desc);
		client.setName(name);
		client.setPrice(price);
		
		try {
			String imagename=saveImage(image);
			client.setImage(imagename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		foodService.save(client);
		return "redirect:/users";
	}

	@RequestMapping(value = "/users/update", method = { RequestMethod.PUT, RequestMethod.GET })
	public String update(User1 country) {
		foodService.save(country);
		return "redirect:/users";
	}

	@RequestMapping(value = "/users/delete/", method = { RequestMethod.DELETE, RequestMethod.GET })
	public String delete(int id) {
		foodService.delete(id);
		return "redirect:/users";
	}

	private String saveImage(MultipartFile image) throws IOException {
		String filename = image.getOriginalFilename();
		Path path = Path.of(UPLOAD_DIR + filename);
		Files.createDirectories(path.getParent());
		Files.write(path, image.getBytes());
		
		return filename;
	}
}