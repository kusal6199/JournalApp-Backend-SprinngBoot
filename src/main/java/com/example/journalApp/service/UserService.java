package com.example.journalApp.service;

import java.util.Arrays;
import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.journalApp.model.User;
import com.example.journalApp.repository.UserRepository;


@Component
public class UserService {

    @Autowired
    private UserRepository repository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        repository.save(user);
    }

    public void deleteUser(int id) {
        repository.deleteById(id);
    }

    public List<User> getAllUser() {
        return repository.findAll();
    }

    public User findUserById(int id) {

        return repository.findById(id).get();
    }

    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }


    @Transactional
    public void deleteByUsername(String username){
        repository.deleteByUsername(username);
    }

    public void saveAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        repository.save(user);
    }


}
