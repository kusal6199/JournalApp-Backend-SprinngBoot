package com.example.journalApp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Indexed;

import java.util.Date;

@Data
@Entity
@Table(name = "journal_entries")
@NoArgsConstructor
public class JournalEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String journalName;
    private String journalContent;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "fk_user_id", nullable = false)
    @JsonBackReference
    private  User user;


}
