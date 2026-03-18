package com.example.mal2021_assessment2.service;

import com.example.mal2021_assessment2.model.*;
import com.example.mal2021_assessment2.repository.LMSCourseRepository;
import com.example.mal2021_assessment2.repository.LMSEnrollmentRepository;
import com.example.mal2021_assessment2.repository.LMSInstructorRepository;
import com.example.mal2021_assessment2.repository.LMSStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LMSService {

    private final LMSCourseRepository courseRepository;
    private final LMSStudentRepository studentRepository;
    private final LMSInstructorRepository instructorRepository;
    private final LMSEnrollmentRepository enrollmentRepository;

    // Constructor
    @Autowired
    public LMSService(LMSCourseRepository courseRepository,
                      LMSStudentRepository studentRepository,
                      LMSInstructorRepository instructorRepository,
                      LMSEnrollmentRepository enrollmentRepository){
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.instructorRepository = instructorRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    // Retrieve Student Enrollment
    public List<Enrollment> getStudentEnrollments(Long studentId){
        return enrollmentRepository.findByStudentId(studentId);
    }

    // List all courses a specific student is enrolled in
    public List<Course> getCoursesForStudent(Long studentId) {
        List<String> enrolledCourseIds = enrollmentRepository.findByStudentId(studentId).stream()
                .map(Enrollment::getCourseId)
                .collect(Collectors.toList());

        return courseRepository.findAllById(enrolledCourseIds);
    }

    // List Active Students
    public List<Student> getActiveStudents() {
        List<Long> activeStudentIds = enrollmentRepository.findAll().stream()
                .map(Enrollment::getStudentId)
                .distinct()
                .collect(Collectors.toList());

        return studentRepository.findAllById(activeStudentIds);
    }

    // Identify Most Active Instructor
    public Instructor getMostActiveInstructor() {
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        List<Course> courses = courseRepository.findAll();

        Map<String, Long> courseToInstructor = courses.stream()
                .collect(Collectors.toMap(Course::getCourseId, Course::getInstructorId, (a, b) -> a));

        Map<Long, Long> instructorCounts = enrollments.stream()
                .map(e -> courseToInstructor.get(e.getCourseId()))
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(id -> id, Collectors.counting()));

        Long topId = instructorCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        return (topId != null) ? instructorRepository.findById(topId).orElse(null) : null;
    }

    // List Instructors with No Enrollments
    public List<Instructor> getInstructorsWithNoEnrollments() {
        Set<String> busyCourseIds = enrollmentRepository.findAll().stream()
                .map(Enrollment::getCourseId)
                .collect(Collectors.toSet());

        Set<Long> activeInstructorIds = courseRepository.findAll().stream()
                .filter(c -> busyCourseIds.contains(c.getCourseId()))
                .map(Course::getInstructorId)
                .collect(Collectors.toSet());

        return instructorRepository.findAll().stream()
                .filter(i -> !activeInstructorIds.contains(i.getInstructorId()))
                .collect(Collectors.toList());
    }

    // List of all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll(); // Fetches everyone in the DB
    }
}
