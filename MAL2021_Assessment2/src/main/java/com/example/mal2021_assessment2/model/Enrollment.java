package com.example.mal2021_assessment2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique ID for each enrollment record

    private Long studentId;
    private String courseId;

    // Constructor
    public Enrollment(){}

    public Enrollment(Long studentId, String courseId){
        this.studentId = studentId;
        this.courseId = courseId;
    }

    // Getters
    public Long getId(){ return id; }
    public Long getStudentId(){ return studentId; }
    public String getCourseId(){ return courseId; }

    // Setters
    public void setId(Long id){ this.id = id; }
    public void setStudentId(Long studentId){ this.studentId = studentId; }
    public void setCourseId(String courseId){ this.courseId = courseId; }
}