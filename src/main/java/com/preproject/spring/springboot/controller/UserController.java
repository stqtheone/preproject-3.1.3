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
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("user", userServiceImpl.loadUserByUsername(name));
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
    public String create(@ModelAttribute("username") String username,
                         @ModelAttribute("password") String password,
                         @ModelAttribute("lastname") String lastname,
                         @ModelAttribute("email") String email,
                         @ModelAttribute("roles") String roles) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setLastname(lastname);
        user.setEmail(email);
        System.out.println(roles.toString());
        HashSet<Role> set = new HashSet<>();
        if(roles.equals("ADMIN")){
            set.add(new Role(2L,"ROLE_ADMIN"));
        } else {
            set.add(new Role(1L,"ROLE_USER"));
        }
        user.setRoles(set);
        userServiceImpl.addUser(user);
        return "redirect:/admin/users";

    }

    @GetMapping("/admin/users/{id}/edit")
    public String editUser(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userServiceImpl.getUser(id));
        return "updateuser";
    }

    @PostMapping("/admin/users/{id}")
    public String updateUser(@ModelAttribute("id") Long id,
                             @ModelAttribute("username") String username,
                             @ModelAttribute("password") String password,
                             @ModelAttribute("lastname") String lastname,
                             @ModelAttribute("email") String email){
        User defaultUser = userServiceImpl.getUser(id);
        defaultUser.setUsername(username);
        defaultUser.setPassword(password);
        defaultUser.setLastname(lastname);
        defaultUser.setEmail(email);
        userServiceImpl.updateUser(defaultUser);
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
