package com.example.mal2021_assessment2.model;

public class Course {
    private Long instructorId;
    private String courseId, title;

    // Constructor
    public Course(){}

    public Course(String courseId, String title, Long instructorId){
        this.courseId = courseId;
        this.title = title;
        this.instructorId = instructorId;
    }

    // Getter
    public String getCourseId(){ return courseId; }
    public String getTitle(){ return title; }
    public Long getInstructorId(){ return instructorId; }

    //Setter
    public void setCourseId(String id){ this.courseId = courseId; }
    public void setTitle(String title){ this.title = title; }
    public void setInstructorId(Long instructorId){this.instructorId = instructorId; }
}
