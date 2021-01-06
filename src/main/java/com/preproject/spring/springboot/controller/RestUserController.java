package com.preproject.spring.springboot.controller;

import com.preproject.spring.springboot.model.User;
import com.preproject.spring.springboot.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestUserController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @RequestMapping(value = "/admin/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> showAllUsers(){
        List<User> userList = userServiceImpl.getAll();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
    @RequestMapping(value = "/autority", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getAutority(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userServiceImpl.loadUserByUsername(name);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @RequestMapping(value = "/admin/users", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> addUser(@RequestBody User user){
        HttpHeaders headers = new HttpHeaders();
        userServiceImpl.addUser(user);
        System.out.println(user);
        return new ResponseEntity<>(user, headers, HttpStatus.CREATED);
    }
    @RequestMapping(value = "/admin/users", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@RequestBody User user){
        HttpHeaders headers = new HttpHeaders();
        System.out.println(user);
        userServiceImpl.updateUser(user);
        return new ResponseEntity<>(user,headers,HttpStatus.OK);
    }
    @RequestMapping(value = "/admin/users", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> deleteUser(@RequestBody User user){
        HttpHeaders headers = new HttpHeaders();
        userServiceImpl.deleteUser(user.getId());
        return new ResponseEntity<>(user,headers,HttpStatus.NO_CONTENT);
    }


}
