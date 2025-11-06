package com.example.journalApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.journalApp.model.User;
import com.example.journalApp.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<User> getAllUsers() {
        return service.getAllUser();
    }

    @PostMapping
    public void createUser(@RequestBody User user) {
        service.saveUser(user);
    }



    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String username) {
        User userInDB = service.findByUsername(username);
        if (userInDB != null) {
            userInDB.setUsername(user.getUsername());
            userInDB.setPassword(user.getPassword());
            service.saveUser(userInDB);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username){
        User user= service.findByUsername(username);
        if (user!=null){
            service.deleteUser(user.getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable String username){
        User user = service.findByUsername(username);
        if (user!=null){
            return new ResponseEntity<>(user,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
