package com.example.myapp.controller;

import com.example.myapp.model.User;
import com.example.myapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller

public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/user")
    public String user(Authentication authentication, Model model) {
        String user = authentication.getName();
        System.out.println(user);
        User userbyname = userService.findByUsername(user);
        model.addAttribute("user", userbyname);
        return "show3";
    }

    @GetMapping("/logt")
    public String logout() {
        return "logout";
    }
}

