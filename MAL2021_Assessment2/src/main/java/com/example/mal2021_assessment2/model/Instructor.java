package com.example.mal2021_assessment2.model;

public class Instructor {
    private Long id;
    private String name, email;

    // Constructor
    public Instructor(){}

    public Instructor(Long id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getter
    public Long getId(){ return id; }
    public String getName(){ return name; }
    public String getEmail(){ return email; }

    // Setter
    public void setId(Long id){ this.id = id; }
    public void setName(String name){ this.name = name; }
    public void setEmail(String email){ this.email = email; }
}
