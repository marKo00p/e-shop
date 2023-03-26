package com.shopy.trainshop.controllers;

import com.shopy.trainshop.dto.UserDTO;
import com.shopy.trainshop.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/")

public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/clients")
    public String userList(Model model){
        model.addAttribute("users",userService.showAll());
        return "clients";
    }
    @GetMapping("/sign_up")
    public String newUser(Model model) {
        model.addAttribute("user", new UserDTO());
        return "sign_up";
    }

    @PostMapping("/sign_up")
    public  String saveUser(UserDTO userDTO, Model model) {
        if (userService.saveUser(userDTO)) {
            return "redirect:/";
        } else {
            model.addAttribute("user", userDTO);
            return "sign_up";
        }
    }
}
