package com.example.springrestapi.repository;

import com.example.springrestapi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findRoleByName(String name);

}
