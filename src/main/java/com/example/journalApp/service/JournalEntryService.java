package com.example.journalApp.service;

import com.example.journalApp.model.JournalEntry;
import com.example.journalApp.model.User;
import com.example.journalApp.repository.JournalRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;


import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Slf4j
@Component
public class JournalEntryService {

    @Autowired
    private JournalRepository repository;

    @Autowired
    private  UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username) {
        User user = userService.findByUsername(username);

        if (user==null){
            log.error("--------------username not found-----------------");
            throw new RuntimeException("User not found with username: " + username);
        }
//        JournalEntry saved = repository.save(journalEntry);
//        user.getJournalEntry().add(saved);
//        userService.saveUser(user);
        journalEntry.setUser(user);
        repository.save(journalEntry);

    }

    public void saveEntry(JournalEntry journalEntry){
        repository.save(journalEntry);
    }

    public List<JournalEntry> getAllJournalEntries() {
        return repository.findAll();
    }

    public Optional<JournalEntry> findEntryById(int id) {
        return repository.findById(id);
    }

    public void deleteByID(int id) {
        repository.deleteById(id);
    }

    public List<JournalEntry> findJournalEntryByUsername(String username){
        User user = userService.findByUsername(username);
        if (user!=null){
            return repository.findAllByUserId(user.getId());
        }

        return Collections.emptyList();

    }

//    public User findUserByUsername(String username){
//        return repository.findUserByUserName(username);
//    }



}
