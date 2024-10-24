package com.example.springrestapi.service;

import com.example.springrestapi.model.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    User getUser(String username);

    List<User> getUsers();

    User updateUser(User user);

    void deleteUser(String username);

}
