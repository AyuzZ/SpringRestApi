package com.example.springrestapi.service;

import com.example.springrestapi.model.User;
import com.example.springrestapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceImplTests {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public User user1 = new User(1, "johndoe", "johndoe", "John", "Doe");
    public User user2 = new User(2, "ram", "ram", "ram", "shrestha");


    @Test
    public void createUserTest(){
        when(userRepository.save(user1)).thenReturn(user1);
        assertEquals(user1, userService.createUser(user1));
    }

    @Test
    public void doesUserExistTest(){
        when(userRepository.findUserByUsername(user1.getUsername())).thenReturn(Optional.of(user1));
//        assertEquals(Optional.of(user).isPresent(), userService.doesUserExist(user.getUsername()) );
        assertTrue(userService.doesUserExist(user1.getUsername()));
    }

    @Test
    public void getUserTest(){
        when(userRepository.findUserByUsername("johndoe")).thenReturn(Optional.of(user1));
        assertEquals(user1, userService.getUser("johndoe"));
    }

    @Test
    public void getUsersTest(){
        userRepository.save(user2);
        when(userRepository.findAll()).thenReturn(Stream
                .of(user1, user2)
                .collect(Collectors.toList()));
        assertEquals(2, userService.getUsers().size());
    }

    @Test
    public void updateUserTest(){
        user2.setFirstName("shyam");
        when(userRepository.save(user2)).thenReturn(user2);
        assertEquals(user2, userService.updateUser(user2));
    }

    @Test
    public void deleteUserTest(){
        userService.deleteUser(user2.getUsername());
        assertFalse(userService.doesUserExist(user2.getUsername()));
    }


}
