package com.preproject.spring.springboot.controller;

import com.preproject.spring.springboot.model.Role;
import com.preproject.spring.springboot.model.User;
import com.preproject.spring.springboot.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping()
public class UserController {
    @Autowired
    UserServiceImpl userServiceImpl;
    @GetMapping("/")
    public String main(Model model) {
        return "main";
    }
    @GetMapping("/admin/users")
    public String showAll(Model model) {
//        String name = SecurityContextHolder.getContext().getAuthentication().getName();
//        model.addAttribute("user", userServiceImpl.loadUserByUsername(name));
//        model.addAttribute("users", userServiceImpl.getAll());
        return "allusers";
    }

    @GetMapping("/user")
    public String showUserById(Model model) {
//        String name = SecurityContextHolder.getContext().getAuthentication().getName();
//        model.addAttribute("user", userServiceImpl.loadUserByUsername(name));
        return "oneuser";
    }
    @GetMapping("/default")
    public String redirectToUserID() {
        return "redirect:/user";
    }


    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

}