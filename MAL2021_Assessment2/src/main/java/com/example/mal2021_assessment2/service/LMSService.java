package com.example.mal2021_assessment2.service;

import com.example.mal2021_assessment2.model.*;
import com.example.mal2021_assessment2.repository.LMSRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

public class LMSService {
    private final LMSRepository lmsRepository;

    // Constructor
    public LMSService(LMSRepository lmsRepository){
        this.lmsRepository = lmsRepository;
    }

    // Retrieve Student Enrollment
    public List<Enrollment> getStudentEnrollments(Long studentId){
        return lmsRepository.getAllEnrollments().stream()
                .filter(e -> e.getStudentId().equals(studentId))
                .collect(Collectors.toList());
    }

    // List all courses a specific student is enrolled in
    public List<Course> getCoursesForStudent(Long studentId){
        Set<String> enrolledCourseIds = lmsRepository.getAllEnrollments().stream()
                .filter(e -> e.getStudentId().equals(studentId))
                .map(Enrollment::getCourseId)
                .collect(Collectors.toSet());

        return lmsRepository.getAllCourses().stream()
                .filter(c -> enrolledCourseIds.contains(c.getCourseId()))
                .collect(Collectors.toList());
    }

    // List Active Students
    public List<Student> getActiveStudents(){
        Set<Long> activeStudentIds = lmsRepository.getAllEnrollments().stream()
                .map(Enrollment::getStudentId)
                .collect(Collectors.toSet());

        return lmsRepository.getAllStudents().stream()
                .filter(s -> activeStudentIds.contains(s.getStudentId()))
                .collect(Collectors.toList());
    }

    // Identify Most Active Instructor
    public Instructor getMostActiveInstructor(){
        List<Enrollment> enrollments = lmsRepository.getAllEnrollments();
        List<Course> courses = lmsRepository.getAllCourses();

        // map courseId to instructorId for quick lookup
        Map<String, Long> courseToInstructor = courses.stream()
                .collect(Collectors.toMap(Course::getCourseId, Course::getInstructorId, (a,b) -> a));

        // count enrollments per instructor
        Map<Long, Long> instructorCounts = enrollments.stream()
                .map(e -> courseToInstructor.get(e.getCourseId()))
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(id -> id, Collectors.counting()));

        Long topId = instructorCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        return lmsRepository.getAllInstructors().stream()
                .filter(i -> i.getInstructorId().equals(topId))
                .findFirst().orElse(null);
    }

    // List Instructors with No Enrollments
    public List<Instructor> getInstructorsWithNoEnrollments(){
        // get course id that have students
        Set<String> busyCourseIds = lmsRepository.getAllEnrollments().stream()
                .map(Enrollment::getCourseId)
                .collect(Collectors.toSet());

        Set<Long> activeInstructorIds = lmsRepository.getAllCourses().stream()
                .filter(c -> busyCourseIds.contains(c.getCourseId()))
                .map(Course::getInstructorId)
                .collect(Collectors.toSet());

        return lmsRepository.getAllInstructors().stream()
                .filter(i -> !activeInstructorIds.contains(i.getInstructorId()))
                .collect(Collectors.toList());
    }
}
