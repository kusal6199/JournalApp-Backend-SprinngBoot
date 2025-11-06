package com.example.journalApp.controller;

import com.example.journalApp.model.JournalEntry;
import com.example.journalApp.model.User;
import com.example.journalApp.service.JournalEntryService;
import com.example.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    JournalEntryService service;

    @Autowired
    UserService userService;

    @PostMapping("{username}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry entry, @PathVariable String username) {
        try {
//            User user = userService.findByUsername(username);
            service.saveEntry(entry, username);
            return new ResponseEntity<>(entry, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

//    @GetMapping("{username}")
//    public ResponseEntity<?> getAllJournalEntryOfUser(@PathVariable String username) {
//
//        User user = userService.findByUsername(username);
//        List<JournalEntry> all = user.getJournalEntry();
//        if (all != null && !all.isEmpty()) {
//            return new ResponseEntity<>(all, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<JournalEntry> findById(@PathVariable int id) {
        Optional<JournalEntry> journalEntry = service.findEntryById(id);
        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteByID(@PathVariable int id) {
        service.deleteByID(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }







    //------------------- this is for future if below update method does not work--------------------------

//    @PutMapping("/update/{id}")
//    public ResponseEntity<JournalEntry> updateJournalEntry(@PathVariable int id, @RequestBody JournalEntry newEntry) {
//
//        JournalEntry old = service.findEntryById(id).orElse(null);
//        if (old != null) {
//            old.setJournalContent(newEntry.getJournalContent() != null && !newEntry.getJournalContent().equals("")
//                    ? newEntry.getJournalContent()
//                    : old.getJournalContent());
//            old.setJournalName(newEntry.getJournalName() != null && !newEntry.getJournalName().equals("")
//                    ? newEntry.getJournalName()
//                    : old.getJournalName());
//            service.saveEntry(old, user);
//            return new ResponseEntity<>(old, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//
//    }

    //---------------------------------EOC end of update code------------------------------------------------





    @GetMapping("/all")
    public List<JournalEntry> listAllJournalEntries(){
        return service.getAllJournalEntries();
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<JournalEntry>> findJournalOfAUser(@PathVariable String username){
        List<JournalEntry> entries = service.findJournalEntryByUsername(username);
        if (entries.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    //we are updating journal entry by the help of username because id cannot be remembered all the time

    @PutMapping("/update/{username}/{myid}")
    public ResponseEntity<JournalEntry> updateJournalByUsername(@PathVariable String username,
                                                                @PathVariable int myid,
                                                                @RequestBody JournalEntry newEntry){
        Optional<JournalEntry> oldEntryObj= service.findEntryById(myid);
        if (oldEntryObj.isPresent()){
            JournalEntry oldEntry =oldEntryObj.get();
            oldEntry.setJournalName(newEntry.getJournalName()!=null && newEntry.getJournalName()!=""? newEntry.getJournalName() : oldEntry.getJournalName());
            oldEntry.setJournalContent(newEntry.getJournalContent()!=null && newEntry.getJournalContent()!=""? newEntry.getJournalContent() : oldEntry.getJournalContent());
            service.saveEntry(oldEntry);
            return new  ResponseEntity<>(oldEntry,HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
