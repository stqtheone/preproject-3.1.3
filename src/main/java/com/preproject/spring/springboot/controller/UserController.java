package com.preproject.spring.springboot.controller;

import com.preproject.spring.springboot.model.Role;
import com.preproject.spring.springboot.model.User;
import com.preproject.spring.springboot.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@Controller
@RequestMapping()
public class UserController {
    @Autowired
    UserServiceImpl userServiceImpl;
    @GetMapping("/")
    public String main(Model model) {
        return "main";
    }
////    @GetMapping("/registration")
////    public String registration(Model model) {
////        model.addAttribute("user", new User());
////        return "registration";
////    }
////    @PostMapping("/registration")
////    public String registrationUser(@ModelAttribute("user") User user, Model model) {
//////        if(userServiceImpl.loadUserByUsername(user.getUsername()) == null){
////            userServiceImpl.addUser(user);
////            return "redirect:/login";
//////        } else{
//////            model.addAttribute("error","Username is already use");
//////            return "registration";
//////        }
//    }
    @GetMapping("/admin/users")
    public String showAll(Model model) {
        model.addAttribute("users", userServiceImpl.getAll());
        return "allusers";
    }

    @GetMapping("/admin/user/{id}")
    public String showUsersById(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userServiceImpl.getUser(id));
        return "oneuser";
    }
    @GetMapping("/user")
    public String showUserById(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("user", userServiceImpl.loadUserByUsername(name));
        return "oneuser";
    }
    @GetMapping("/default")
    public String redirectToUserID() {
        return "redirect:/user";
    }

    @GetMapping("/admin/users/adduser")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "adduser";
    }


    @PostMapping("/admin/users")
    public String create(@ModelAttribute("user") User user, @ModelAttribute("userRole") String userRole,
                         @ModelAttribute("adminRole") String adminRole) {
        user.setRoles(new HashSet<>());
        if (userRole.equals("on")){
            user.getRoles().add(new Role(1L, "ROLE_USER"));
        }
        if (adminRole.equals("on")){
            user.getRoles().add(new Role(2L, "ROLE_ADMIN"));
        }
        userServiceImpl.addUser(user);
        return "redirect:/admin/users";

    }

    @GetMapping("/admin/users/{id}/edit")
    public String editUser(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userServiceImpl.getUser(id));
        return "updateuser";
    }

    @PostMapping("/admin/users/{id}")
    public String updateUser(@ModelAttribute("user") User user){
        User defaultUser = userServiceImpl.loadUserByUsername(user.getUsername());
        user.setRoles(defaultUser.getRoles());
        userServiceImpl.updateUser(user);
        System.out.println("POST MAPPING OK");
        return "redirect:/admin/users";
    }

    @DeleteMapping("/admin/users/{id}")
    public String deleteUser(@ModelAttribute("id") Long id){
        userServiceImpl.deleteUser(id);
        System.out.println(id);
        return "redirect:/admin/users";
    }
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

}
