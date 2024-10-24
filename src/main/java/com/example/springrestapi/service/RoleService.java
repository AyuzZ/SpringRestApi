package com.example.springrestapi.service;

import com.example.springrestapi.model.Role;

import java.util.List;

public interface RoleService {

    Role createRole(Role role);

    Role getRole(String name);

    List<Role> getRoles();
}
