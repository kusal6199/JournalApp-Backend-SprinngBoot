package com.example.journalApp.controller;

import com.example.journalApp.model.JournalEntry;
import com.example.journalApp.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    JournalEntryService service;

    @PostMapping("/postEntry")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry entry){
        try {
            service.saveEntry(entry);
            return new ResponseEntity<>(entry, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping("/findAll")
   public ResponseEntity<?> getAllEntries(){

        List<JournalEntry> all = service.getAlljournalEntries();
        if(all!=null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   }



    @GetMapping("/findById/{id}")
   public ResponseEntity<JournalEntry> findById(@PathVariable int id){
       Optional<JournalEntry> journalEntry= service.findEntryById(id);
       if (journalEntry.isPresent()){
           return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
       }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   }

   @DeleteMapping("/delete/{id}")
   public ResponseEntity<?> deleteByID(@PathVariable int id){
        service.deleteByID(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }

   @PutMapping("/update/{id}")
   public ResponseEntity<JournalEntry> updateJournalEntry(@PathVariable int id, @RequestBody JournalEntry newEntry){

        JournalEntry old = service.findEntryById(id).orElse(null);
        if(old!=null){
            old.setJournalContent(newEntry.getJournalContent()!=null && !newEntry.getJournalContent().equals("")? newEntry.getJournalContent(): old.getJournalContent());
            old.setJournalName(newEntry.getJournalName()!=null && !newEntry.getJournalName().equals("")? newEntry.getJournalName(): old.getJournalName());
            service.saveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


   }


}
