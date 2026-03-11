package com.example.mal2021_assessment2.model;

public class Enrollment {
    private Long studentId;
    private String courseId;

    // Constructor
    public Enrollment(){}

    public Enrollment(Long studentId, String courseId){
        this.studentId = studentId;
        this.courseId = courseId;
    }

    // Getter
    public Long getStudentId(){ return studentId; }
    public String getCourseId(){ return courseId; }

    // Setter
    public void setStudentId(Long studentId){ this.studentId = studentId; }
    public void setCourseId(String courseId){ this.courseId = courseId; }
}
