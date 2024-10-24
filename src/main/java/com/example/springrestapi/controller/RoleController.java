package com.example.springrestapi.controller;

import com.example.springrestapi.exceptions.RoleExistsException;
import com.example.springrestapi.model.Role;
import com.example.springrestapi.model.User;
import com.example.springrestapi.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role/")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles(){
        List<Role> roleList = roleService.getRoles();
        return new ResponseEntity<>(roleList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody Role role){
        if (roleService.getRole(role.getName()) == null){
            Role createdRole = roleService.createRole(role);
            return new ResponseEntity<>("Role Created.", HttpStatus.CREATED);
        }else {
            throw new RoleExistsException("Role already exists.");
        }
    }

}
