package com.example.springrestapi.service;

import com.example.springrestapi.model.User;

import java.util.List;

public interface UserService {

    boolean doesUserExist(String username);

    User createUser(User user);

    User getUser(String username);

    List<User> getUsers();

    User updateUser(User user);

    boolean deleteUser(String username);

}
