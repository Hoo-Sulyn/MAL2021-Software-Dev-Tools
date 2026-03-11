package com.example.mal2021_assessment2.controller;

import com.example.mal2021_assessment2.model.*;
import com.example.mal2021_assessment2.service.LMSService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/lms")
public class LMSController {
    private final LMSService lmsService;

    // Constructor
    public LMSController(LMSService lmsService){
        this.lmsService = lmsService;
    }

    // Retrieve Student Enrollments
    @GetMapping("/enrollments/{studentId")
    public List<Enrollment> getStudentEnrollments(@PathVariable Long studentId){
        return lmsService.getStudentEnrollments(studentId);
    }

    // List Active Students
    @GetMapping("/students/active")
    public List<Student> getActiveStudents(){
        return lmsService.getActiveStudents();
    }

    // Identify Most Active Instructor
    @GetMapping("/instructos/most-active")
    public Instructor getMostActiveInstructor(){
        return lmsService.getMostActiveInstructor();
    }

    // List Instructors with No Enrollments
    @GetMapping("/instructos/none")
    public List<Instructor> getInstructorsWithNoEnrollments(){
        return lmsService.getInstructorsWithNoEnrollments();
    }
}
