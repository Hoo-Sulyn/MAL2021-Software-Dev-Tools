package com.example.mal2021_assessment2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
public class Course {
    @Id
    private String courseId; // Assuming code is your primary key

    private String title;
    private Long instructorId;

    // IMPORTANT: JPA requires a no-args constructor
    public Course() {}

    public Course(String courseId, String title, Long instructorId) {
        this.courseId = courseId;
        this.title = title;
        this.instructorId = instructorId;
    }

    // Getter
    public String getCourseId(){ return courseId; }
    public String getTitle(){ return title; }
    public Long getInstructorId(){ return instructorId; }

    //Setter
    public void setCourseId(String courseId){ this.courseId = courseId; }
    public void setTitle(String title){ this.title = title; }
    public void setInstructorId(Long instructorId){this.instructorId = instructorId; }
}
