package com.preproject.spring.springboot.controller;

import com.preproject.spring.springboot.model.User;
import com.preproject.spring.springboot.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestUserController {
    @Autowired
    private UserServiceImpl userServiceImpl;


    @GetMapping("/admin/users")
    public List<User> showAllUsers(){
        List<User> userList = userServiceImpl.getAll();
        return userList;
    }
    @GetMapping("/autority")
    public User getAutority(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userServiceImpl.loadUserByUsername(name);
        return user;
    }

    @GetMapping("/user/{id}")
    public User showUser(@PathVariable int id){
        User user = userServiceImpl.getUser(id);
        return user;
    }



    @PostMapping("/admin/users")
    public User addUser(@RequestBody User user){
        userServiceImpl.addUser(user);
        System.out.println(user);
        return user;
    }

    @PutMapping("/admin/users")
    public User updateUser(@RequestBody User user){
        System.out.println(user);
        userServiceImpl.updateUser(user);
        return user;
    }

    @DeleteMapping("/admin/users")
    public void deleteUser(@RequestBody User user){
        userServiceImpl.deleteUser(user.getId());
    }


}
