package com.example.springrestapi.controller;

import com.example.springrestapi.model.Role;
import com.example.springrestapi.model.User;
import com.example.springrestapi.service.RoleService;
import com.example.springrestapi.service.UserService;
import com.example.springrestapi.service.impl.UserDetailsServiceImpl;
import com.example.springrestapi.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;


    //No Auth Required

    @GetMapping({"", "/"})
    public ResponseEntity<?> getHome(){
        return new ResponseEntity<>("Log in to an account to see your details.", HttpStatus.OK);
    }

    @PostMapping("/createUser/")
    public ResponseEntity<?> createUser(@RequestBody User user){
        //If the user exists or not is checked in UserServiceImpl

        BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = pwEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        Role userRole = roleService.getRole("USER");
        if (userRole == null) {
            throw new RuntimeException("Role not found");
        }
        user.getRoles().add(userRole);

//        boolean createdResult = userService.createUser(user);
        User createdUser = userService.createUser(user);
//        if (createdResult)
            return new ResponseEntity<>("User Created.", HttpStatus.CREATED);
//        return new ResponseEntity<>("User Creation Failed.", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login/")
    public ResponseEntity<String> login(@RequestBody User user){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Incorrect username or password.", HttpStatus.BAD_REQUEST);
        }
    }


    //User Access

    @GetMapping("/user/")
    public ResponseEntity<User> getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.getUser(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/user/")
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User existingUser = userService.getUser(username);

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());

//        boolean updateUserResult = userService.updateUser(existingUser);
        User updatedUser = userService.updateUser(existingUser);

//        if (updateUserResult)
            return new ResponseEntity<>("User Updated.", HttpStatus.OK);
//        return new ResponseEntity<>("User Update Failed.", HttpStatus.BAD_REQUEST);
    }


    //Admin Access

    @GetMapping({"/admin", "/admin/"})
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> userList = userService.getUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username){
        User user = userService.getUser(username);
        if(user == null){
            throw new UsernameNotFoundException("User Not Found.");
        }
        List<Role> roles = user.getRoles();
        for (Role role : roles) {
            role.getUsers().clear();
        }
        boolean isDeleted = userService.deleteUser(username);
        if(isDeleted)
            return new ResponseEntity<>("User Deleted.", HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>("User Could Not Be Deleted.", HttpStatus.NOT_FOUND);
    }
}
