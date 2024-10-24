package com.example.springrestapi.repository;

import com.example.springrestapi.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByUsername(String username);

//    @Query("SELECT u FROM User u WHERE u.username=?1")
//    Optional<User> getUserByUsername(String username);

    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.username=?1")
    void deleteUserByUsername(String username);

}
