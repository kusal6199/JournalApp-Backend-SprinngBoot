package com.example.journalApp.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
@Entity
@Table(name = "journal_entries")
public class JournalEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private  String journalName;
    private String journalContent;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

}


