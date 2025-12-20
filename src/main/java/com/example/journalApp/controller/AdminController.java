package com.example.journalApp.controller;

import com.example.journalApp.model.User;
import com.example.journalApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")


@Slf4j
public class AdminController {

    @Autowired
    UserService userService;

    @GetMapping("/all-user")
   public ResponseEntity<?> getAllUser(){
       List<User> allUser = userService.getAllUser();
       if (allUser!=null && !allUser.isEmpty()){
           return new ResponseEntity<>(allUser, HttpStatus.OK);
       }
       return new ResponseEntity<>("no any user found",HttpStatus.NOT_FOUND);
   }

   @PostMapping("/create-admin-user")
   public ResponseEntity<String> createAdmin(@RequestBody User user){
        userService.saveAdmin(user);
        log.info("--------------admin is created successfully------------");
        return new ResponseEntity<>("admin created success",HttpStatus.OK);
   }

//   public ResponseEntity<String> updateAdmin(@RequestBody User user){
//       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//       String userInDb= authentication.getName();
//       if (userInDb!=null && )
//
//   }

}
