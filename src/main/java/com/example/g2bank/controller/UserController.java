package com.example.g2bank.controller;

import com.example.g2bank.model.User;
import com.example.g2bank.repository.DummyLoginRepository;
import com.example.g2bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserRepository repository;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User loggedInUser = repository.getUserByEmail(user.getEmail());
        if (loggedInUser != null && loggedInUser.getPassword().equals(user.getPassword())) {
            DummyLoginRepository.setLoggedInUser(loggedInUser);
            return "success";
        }
        return "fail";

    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {

        String encryptedPassword = encoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        repository.save(user);
        return "success";
    }

}
