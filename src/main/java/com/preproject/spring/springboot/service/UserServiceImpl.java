package com.preproject.spring.springboot.service;

import com.preproject.spring.springboot.dao.RoleRepository;
import com.preproject.spring.springboot.dao.UserRepository;
import com.preproject.spring.springboot.model.Role;
import com.preproject.spring.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public void addUser(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        Role role = new Role(1L, "ROLE_USER");
//        Role role2 = new Role(2L, "ROLE_ADMIN");
//        roleRepository.save(role);
//        roleRepository.save(role2);
//        Set<Role> set = new HashSet<>();
//        set.add(role);
//        set.add(role2);
//        user.setRoles(set);
        userRepository.save(user);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User getUser(long id){
        Optional<User> userFromDb = userRepository.findById(id);
        return userFromDb.get();
    }

    public User loadUserByUsername(String username){
        User user = userRepository.findUserByUsername(username);
        return user;
    }

    public void updateUser(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
