package com.example.journalApp.repository;

import com.example.journalApp.model.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JournalRepository extends JpaRepository<JournalEntry,Integer> {
 List<JournalEntry> findAllByUserId(int id);
}
