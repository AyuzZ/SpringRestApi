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
    public boolean doesUserExist(String username) {
        Optional<User> optionalUser = userRepository.findUserByUsername(username);
        return optionalUser.isPresent();
    }

    @Override
    public User createUser(User user) {
        boolean doesUserExist = doesUserExist(user.getUsername());
        if(doesUserExist){
            throw new UserExistsException("User Already Exists.");
        }else {
//            try{
              return userRepository.save(user);
//                return true;
//            }catch (Exception e){
//                return false;
//            }
        }
    }

    @Override
    public User getUser(String username) {
        boolean doesUserExist = doesUserExist(username);
        if(!doesUserExist){
            throw new UsernameNotFoundException("User Not Found.");
        }else{
            Optional<User> optionalUser = userRepository.findUserByUsername(username);
            User user = optionalUser.get();
            return user;
        }
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(User user) {
//        try{
        return userRepository.save(user);
//            return true;
//        }catch (Exception e){
//            return false;
//        }
    }

    @Override
    public boolean deleteUser(String username) {
        userRepository.deleteUserByUsername(username);
        return true;
    }

}
