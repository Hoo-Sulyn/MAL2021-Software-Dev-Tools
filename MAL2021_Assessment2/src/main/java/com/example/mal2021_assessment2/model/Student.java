package com.example.mal2021_assessment2.model;

public class Student {
    private Long studentId;
    private String name, email;

    // Constructor
    public Student(){}

    public Student(Long studentId, String name, String email){
        this.studentId = studentId;
        this.name = name;
        this.email = email;
    }

    // Getter
    public Long getStudentId(){ return studentId; }
    public String getName(){ return name; }
    public String getEmail(){ return email; }

    // Setter
    public void setStudentId(Long id){ this.studentId = studentId; }
    public void setName(String name){ this.name = name; }
    public void setEmail(String email){ this.email = email; }
}
