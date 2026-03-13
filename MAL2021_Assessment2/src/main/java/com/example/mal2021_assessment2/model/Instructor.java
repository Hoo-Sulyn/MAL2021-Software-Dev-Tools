package com.example.mal2021_assessment2.model;

public class Instructor {
    private Long instructorId;
    private String name, email;

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
    public void setId(Long instructorId){ this.instructorId = instructorId; }
    public void setName(String name){ this.name = name; }
    public void setEmail(String email){ this.email = email; }
}
