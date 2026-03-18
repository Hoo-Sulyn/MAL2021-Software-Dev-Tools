package com.example.mal2021_assessment2.controller;

import com.example.mal2021_assessment2.model.*;
import com.example.mal2021_assessment2.repository.LMSStudentRepository;
import com.example.mal2021_assessment2.service.LMSService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/lms")
public class LMSController {
    @Autowired
    private final LMSService lmsService;

    @Autowired
    private LMSStudentRepository studentRepo;

    // Constructor
    public LMSController(LMSService lmsService){
        this.lmsService = lmsService;
    }

    // Retrieve Student Enrollments
//    @GetMapping("/enrollments/{studentId}")
//    public List<Enrollment> getStudentEnrollments(@PathVariable Long studentId){
//        return lmsService.getStudentEnrollments(studentId);
//    }

    @GetMapping("/enrollments/{studentId}")
    public ResponseEntity<Object> getStudentEnrollments(@PathVariable Long studentId) {
        // 1. Check if student exists
        java.util.Optional<Student> studentOpt = studentRepo.findById(studentId);

        if (studentOpt.isEmpty()) {
            // 2. Return the String error with 404
            return ResponseEntity.status(404)
                    .body("Error: Student ID " + studentId + " does not exist in the system.");
        }

        // 3. If they exist, build the Map
        Student student = studentOpt.get();
        List<Enrollment> enrollments = lmsService.getStudentEnrollments(studentId);

        java.util.Map<String, Object> response = new java.util.LinkedHashMap<>();
        response.put("studentId", studentId);
        response.put("studentName", student.getName());
        response.put("enrollments", enrollments);

        if (enrollments.isEmpty()) {
            response.put("message", "This student has no current enrollments.");
        }

        return ResponseEntity.ok(response);
    }

    // List Active Students
    @GetMapping("/students/active")
    public List<Student> getActiveStudents(){
        return lmsService.getActiveStudents();
    }

    // Identify Most Active Instructor
    @GetMapping("/instructors/most-active")
    public Instructor getMostActiveInstructor(){
        return lmsService.getMostActiveInstructor();
    }

    // List Instructors with No Enrollments
    @GetMapping("/instructors/none")
    public List<Instructor> getInstructorsWithNoEnrollments(){
        return lmsService.getInstructorsWithNoEnrollments();
    }
}
