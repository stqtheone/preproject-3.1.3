package com.preproject.spring.springboot.dao;

import com.preproject.spring.springboot.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
