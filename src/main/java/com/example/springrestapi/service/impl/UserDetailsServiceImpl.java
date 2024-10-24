package com.example.springrestapi.service.impl;

import com.example.springrestapi.model.Role;
import com.example.springrestapi.model.User;
import com.example.springrestapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findUserByUsername(username);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRoles()
                            .stream()
                            .map(Role::getName)
                            .toArray(String[]::new))
                    //role -> role.getName() = Role::getName (using method reference instead of lambda expression)
                    //.map() returns Stream<String> containing role names
                    //.toArray collects stream elements into an Array
                    //String[]::new -> reference to array constructor to create new array of strings
                    .build();
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
