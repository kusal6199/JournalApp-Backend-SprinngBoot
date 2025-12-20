package com.example.journalApp.repository;

import com.example.journalApp.model.JournalEntry;
import com.example.journalApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JournalRepository extends JpaRepository<JournalEntry,Integer> {
 List<JournalEntry> findAllByUserId(int id);
// User findUserByUserName(String username);
}
