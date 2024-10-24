package com.example.springrestapi.service.impl;

import com.example.springrestapi.exceptions.UserExistsException;
import com.example.springrestapi.model.User;
import com.example.springrestapi.repository.UserRepository;
import com.example.springrestapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        Optional<User> optionalUser = userRepository.findUserByUsername(user.getUsername());
        if(optionalUser.isPresent()){
            throw new UserExistsException("User Already Exists.");
        }else {
              return userRepository.save(user);
        }
    }

    @Override
    public User getUser(String username) {
        Optional<User> optionalUser = userRepository.findUserByUsername(username);
        if(optionalUser.isPresent()){
            return optionalUser.get();
        }
        throw new UsernameNotFoundException("User Not Found.");
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String username) {
        userRepository.deleteUserByUsername(username);
    }

}
