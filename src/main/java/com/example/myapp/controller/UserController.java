package com.example.myapp.controller;



import com.example.myapp.model.User;
import com.example.myapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String allusers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "/index";
    }

    @GetMapping("users/{id}")
    public String show(@PathVariable() Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute() User user) {
        return "/new";
    }

    @PostMapping("/users")
    public String create(@Valid @ModelAttribute() User user,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/new";
        }

        userService.saveUser(user);
        return "redirect:/";
    }

    @GetMapping("users/{id}/edit")
    public String edit(Model model, @PathVariable() Long id) {
        model.addAttribute("user", userService.findById(id));
        return "/edit";
    }

    @PatchMapping("{id}/update")
    public String update(@Valid @ModelAttribute() User user, BindingResult bindingResult,
                         @PathVariable() long id) {
        if (bindingResult.hasErrors()) {
            return "/edit";
        }

        userService.saveUser(user);
        return "redirect:/";
    }

    @DeleteMapping("users/{id}/delete")
    public String delete(@PathVariable() Long id) {
        userService.deleteById(id);
        return "redirect:/";
    }


}