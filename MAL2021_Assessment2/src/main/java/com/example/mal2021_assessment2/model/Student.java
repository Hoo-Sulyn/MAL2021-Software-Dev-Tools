package com.example.mal2021_assessment2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    private String name;
    private String email;

    // Constructor
    public Student(){}

    public Student(Long studentId, String name, String email){
        this.studentId = studentId;
        this.name = name;
        this.email = email;
    }

    // Getters
    public Long getStudentId(){ return studentId; }
    public String getName(){ return name; }
    public String getEmail(){ return email; }

    // Setters
    public void setStudentId(Long id){ this.studentId = id; }
    public void setName(String name){ this.name = name; }
    public void setEmail(String email){ this.email = email; }
}