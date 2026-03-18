package com.example.mal2021_assessment2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Database handles the ID auto-increment
    private Long instructorId;

    private String name;
    private String email;

    // Constructor
    public Instructor(){}

    public Instructor(Long instructorId, String name, String email){
        this.instructorId = instructorId;
        this.name = name;
        this.email = email;
    }

    // Getter
    public Long getInstructorId(){ return instructorId; }
    public String getName(){ return name; }
    public String getEmail(){ return email; }

    // Setter
    public void setInstructorId(Long instructorId){ this.instructorId = instructorId; }
    public void setName(String name){ this.name = name; }
    public void setEmail(String email){ this.email = email; }
}
