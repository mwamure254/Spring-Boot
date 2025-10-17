package com.mfano.moe.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mfano.moe.security.models.User;
import com.mfano.moe.security.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    public boolean checkIfUserExist(String email) {

        return userRepository.findByEmail(email) != null;
    }

    //Get All Users
    public List<User> findAll() {
        return userRepository.findAll();
    }

    //Get User By Id
    public User findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    //Delete User
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    //Update User
    public void register(User user) {
        if (checkIfUserExist(user.getEmail())) {
           
        }
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

}
