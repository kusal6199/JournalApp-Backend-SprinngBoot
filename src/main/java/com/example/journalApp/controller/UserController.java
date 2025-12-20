package com.example.journalApp.controller;

import java.util.List;

import com.example.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.journalApp.model.User;
import com.example.journalApp.service.UserService;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

//    @Autowired
//    private UserRepository repository;



//    @GetMapping
//    public List<User> getAllUsers() {
//        return service.getAllUser();
//    }   this is handled by admin




    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userInDB = service.findByUsername(username);
            userInDB.setUsername(user.getUsername());
            userInDB.setPassword(user.getPassword());
            service.saveUser(userInDB);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        service.deleteByUsername(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable String username){
        User user = service.findByUsername(username);
        if (user!=null){
            return new ResponseEntity<>(user,HttpStatus.OK);
        }
        log.info("---------------------no user found-------------------");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
