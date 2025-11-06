package com.example.journalApp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "user")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

   @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
//   @JoinColumn(name = "fk_user_id", referencedColumnName = "id")
   @JsonManagedReference
   private List<JournalEntry> journalEntry;

}
