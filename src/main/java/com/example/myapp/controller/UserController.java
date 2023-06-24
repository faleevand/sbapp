package com.example.myapp.controller;



import com.example.myapp.model.Role;
import com.example.myapp.model.User;
import com.example.myapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/admin")
    public String allUsers(Model model) {
        model.addAttribute("users", userService.allUsers());
        return "admin";
    }

    @GetMapping("users/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute User user) {
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
        return "userEdit";
    }

    @PostMapping("users/save")
    public String update(@Valid @ModelAttribute User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "users/{id}/edit";
        }

        userService.save(user);

        return "redirect:/admin";
    }

    @DeleteMapping("users/{id}/delete")
    public String delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String user(Authentication authentication, Model model) {
        String user = authentication.getName();
        System.out.println(user);
        User userbyname = userService.findByUsername(user);
        model.addAttribute("user", userbyname);
        return "show";
    }

    @GetMapping("/logt")
    public String logout() {
        return "logout";
    }

//    @PostMapping("/user")
//    public String regsucces() {
//        return "regsuccess";
//    }

    //    @GetMapping("/")
//    public String allusers() {
//       // model.addAttribute("users", userService.allUsers());
//        return "index";
//    }
    //    @GetMapping("users/{id}/edit")
//    public String edit(Model model, @PathVariable() Long id) {
//        model.addAttribute("user", userService.findUserById(id));
//        return "/edit";
//    }
//
//    @PatchMapping("{id}/update")
//    public String update(@Valid @ModelAttribute() User user, BindingResult bindingResult,
//                         @PathVariable() long id) {
//        if (bindingResult.hasErrors()) {
//            return "/edit";
//        }
//
//        userService.saveUser(user);
//
//        //return "redirect:/users";
//        return "save";
//    }
//    @GetMapping("users/{id}/edit")
//    public String edit(Model model, @PathVariable() Long id) {
//        model.addAttribute("user", userService.findUserById(id));
//        return "user_form" ;
//    }
//
//    @PostMapping("/users")
//    public String create(@Valid @ModelAttribute() User user,
//                         BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "/new";
//        }
//
//        userService.saveUser(user);
//        return "redirect:/users";
//    }

}