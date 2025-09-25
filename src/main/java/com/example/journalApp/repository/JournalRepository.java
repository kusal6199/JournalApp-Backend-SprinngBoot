package com.example.journalApp.repository;

import com.example.journalApp.model.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalRepository extends JpaRepository<JournalEntry,Integer> {
}
