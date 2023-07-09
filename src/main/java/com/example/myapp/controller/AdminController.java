package com.example.myapp.controller;


import com.example.myapp.model.Role;
import com.example.myapp.model.User;
import com.example.myapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;


@Controller
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/admin")
    public String welcomePage() {
        return "indexRest";
    }











//-----------------------------------------------------------------------------------------------------------

//Контроллеры из предыдущего задания. Не используются.



    @GetMapping("/test")
    public String allUsers(Model model) {
        model.addAttribute("users", userService.allUsers());

        return "admin";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute User user) {
        return "/new2";
    }

    @GetMapping("/new2")
    public String newPerson2(@ModelAttribute User user) {
        return "/new";
    }

    @PostMapping("/user")
    public String create(@Valid @ModelAttribute User user,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/new";
        }
        userService.saveUser(user);
        return "regsuccess";
    }

    @GetMapping("users/{id}/edit")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userService.findUserById(id);
        List<Role> listRoles = userService.listRoles();
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        return "userEditAdmin";
    }


    @PostMapping("users/save")
    public String update(@Valid @ModelAttribute User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "error";
        }

        userService.save(user);

        return "redirect:/admin";
    }

    @PostMapping("users/{id}/delete")
    public String delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
