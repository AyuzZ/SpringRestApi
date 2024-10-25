package com.example.springrestapi.service;

import com.example.springrestapi.model.Role;
import com.example.springrestapi.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RoleServiceImplTests {

    @MockBean
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    public Role role1 = new Role(1, "TestRole1");
    public Role role2 = new Role(2, "TestRole2");

    @Test
    public void createRoleTest(){
        when(roleRepository.save(role1)).thenReturn(role1);
        assertEquals(role1, roleService.createRole(role1));
    }

    @Test
    public void getRoleTest(){
        when(roleRepository.findRoleByName(role1.getName())).thenReturn(Optional.of(role1));
        assertEquals(role1, roleService.getRole(role1.getName()));
    }

    @Test
    public void getRolesTest(){
        roleRepository.save(role2);
        when(roleRepository.findAll()).thenReturn(Stream
                .of(role1, role2)
                .collect(Collectors.toList()));
        assertEquals(2, roleService.getRoles().size());
    }

}
