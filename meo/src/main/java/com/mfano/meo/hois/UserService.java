package com.mfano.meo.hois;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	private UserRepository foodRepository;

	public List<UserModel> getAllFoods() {
		return foodRepository.findAll();
	}

	public UserModel getFoodById(Long id) {
		Optional<UserModel> food = foodRepository.findById(id);
		return food.orElse(null);
	}

	public UserModel saveFood(UserModel food) {
		return foodRepository.save(food);
	}

}
