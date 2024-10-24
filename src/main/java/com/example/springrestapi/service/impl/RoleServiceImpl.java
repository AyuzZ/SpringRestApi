package com.example.springrestapi.service.impl;

import com.example.springrestapi.model.Role;
import com.example.springrestapi.repository.RoleRepository;
import com.example.springrestapi.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role getRole(String name) {
        if(roleRepository.findRoleByName(name).isPresent())
            return roleRepository.findRoleByName(name).get();
        return null;
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
}
