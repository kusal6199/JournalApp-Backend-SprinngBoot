package com.example.journalApp.controller;


// this class is created because to give access to public end points like crating user..

import com.example.journalApp.model.User;
import com.example.journalApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/public")

@Slf4j
public class PublicController {

    @Autowired
    private UserService userService;


    @PostMapping
    public void createUser(@RequestBody User user){
        userService.saveUser(user);
        log.info("-------------------user is created-------------------");
    }
}
