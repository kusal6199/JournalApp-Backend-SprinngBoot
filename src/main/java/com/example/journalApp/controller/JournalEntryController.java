package com.example.journalApp.controller;

import com.example.journalApp.model.JournalEntry;
import com.example.journalApp.model.User;
import com.example.journalApp.service.JournalEntryService;
import com.example.journalApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    JournalEntryService service;

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry entry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        if(user!=null){
            Optional<JournalEntry> journalEntry = service.findEntryById(id);
            JournalEntry entry = journalEntry.get();
            if (entry.getUser().getId()==user.getId()) {
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
           return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteByID(@PathVariable int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        if (user!=null){
            Optional<JournalEntry> entryObj = service.findEntryById(id);
            if (entryObj.isEmpty()){
                return new ResponseEntity<>("journal not found",HttpStatus.NOT_FOUND);
            }
            JournalEntry entry = entryObj.get();
            if (entry.getUser().getId()==user.getId()){
                service.deleteByID(id);
                return new ResponseEntity<>("success",HttpStatus.NO_CONTENT);
            }
        }

        return  new ResponseEntity<>("Unauthorized ",HttpStatus.NOT_FOUND);
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





//    @GetMapping("/all")
//    public List<JournalEntry> listAllJournalEntries(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username
//
//        return service.getAllJournalEntries();
//    }

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalOfAUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<JournalEntry> entries = service.findJournalEntryByUsername(username);
        if (entries.isEmpty()) {
            log.info("---------------------------------entry of journal for user: {} is empty------------------------",username);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("---------------------------------entry of journal for user: {} is found------------------------",username);
        return new ResponseEntity<>(entries, HttpStatus.OK);

    }

    //we are updating journal entry by the help of username because id cannot be remembered all the time

    @PutMapping("/update/{myid}")
    public ResponseEntity<JournalEntry> updateJournalByUsername(@PathVariable int myid,
                                                                @RequestBody JournalEntry newEntry){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        if(user!= null) {
            Optional<JournalEntry> oldEntryObj = service.findEntryById(myid);
            if (oldEntryObj.isPresent()) {
                JournalEntry oldEntry = oldEntryObj.get();
                if (oldEntry.getUser().getId()==user.getId()) {
                    oldEntry.setJournalName(newEntry.getJournalName() != null && newEntry.getJournalName() != "" ? newEntry.getJournalName() : oldEntry.getJournalName());
                    oldEntry.setJournalContent(newEntry.getJournalContent() != null && newEntry.getJournalContent() != "" ? newEntry.getJournalContent() : oldEntry.getJournalContent());
                    service.saveEntry(oldEntry);
                    log.info("-----------------------journal entry with ID {} is updated---------------------------- ",myid);
                    return new ResponseEntity<>(oldEntry, HttpStatus.OK);
                }
                log.info("---------------username: {} is unauthorized for this journal entry-----------------------",username);
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            log.info("----------------------journal entry with id: {} is not present-------------------------------",myid);
        }


        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
