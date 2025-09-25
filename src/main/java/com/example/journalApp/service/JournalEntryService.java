package com.example.journalApp.service;


import com.example.journalApp.model.JournalEntry;
import com.example.journalApp.repository.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalRepository repository;


    public void saveEntry(JournalEntry journalEntry){
        repository.save(journalEntry);
    }

    public List<JournalEntry> getAlljournalEntries(){
        return repository.findAll();
    }

    public Optional<JournalEntry> findEntryById(int id){
        return repository.findById(id);
    }


    public void deleteByID(int id){
        repository.deleteById(id);
    }



}
