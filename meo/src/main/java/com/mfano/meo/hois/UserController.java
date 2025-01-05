package com.mfano.meo.hois;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService foodService;
	private final String UPLOAD_DIR = "/..img/uploads/users";

	@GetMapping
	public ResponseEntity<?> getAllFoods() {
		return ResponseEntity.ok(foodService.getAllFoods());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getFoodById(@PathVariable Long id) {
		UserModel food = foodService.getFoodById(id);
		if (food != null) {
			return ResponseEntity.ok(food);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/add")
	public ResponseEntity<?> addUser(@RequestParam String name, @RequestParam String description,
			@RequestParam double price, @RequestParam MultipartFile image) {
		try {
			String imageFilename = saveImage(image);
			UserModel food = new UserModel();
			food.setName(name);
			food.setDescription(description);
			food.setPrice(price);
			food.setImageFilename(imageFilename);
			UserModel savedFood = foodService.saveFood(food);
			return ResponseEntity.ok(savedFood);
		} catch (IOException e) {
			return ResponseEntity.status(500).body("Failed to upload image");
		}
	}

	private String saveImage(MultipartFile image) throws IOException {
		String filename = image.getOriginalFilename();
		Path path = Paths.get(UPLOAD_DIR + filename);
		Files.createDirectories(path.getParent());
		Files.write(path, image.getBytes());
		return filename;
	}
}