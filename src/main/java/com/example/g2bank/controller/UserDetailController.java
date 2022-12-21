package com.example.g2bank.controller;


import com.example.g2bank.model.User;
import com.example.g2bank.model.UserDetail;
import com.example.g2bank.repository.UserDetailRepository;
import com.example.g2bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserDetailController {

    @Autowired
    private UserDetailRepository repository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/balance/")
    @Cacheable(value = "balance")
    public List<UserDetail> getUserDetail() {
        List<UserDetail> userDetails = repository.findAll();
        for (UserDetail userDetail: userDetails) {
            userDetail.setUser(null);
        }
        return userDetails;
    }

    @PostMapping("/balance/")
    public String save(@RequestBody UserDetail userDetail, Principal principal) {
        User user = userRepository.getUserByEmail(principal.getName());
        userDetail.setUser(user);
        repository.save(userDetail);
        return "success";
    }
}
