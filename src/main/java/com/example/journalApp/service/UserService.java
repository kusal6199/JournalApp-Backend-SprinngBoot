package com.example.journalApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.journalApp.model.User;
import com.example.journalApp.repository.UserRepository;

@Component
public class UserService {

    @Autowired
    UserRepository repository;

    public void saveUser(User user) {
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

}
