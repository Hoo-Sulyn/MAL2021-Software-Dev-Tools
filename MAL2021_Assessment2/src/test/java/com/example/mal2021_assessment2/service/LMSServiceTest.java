package com.example.mal2021_assessment2.service;

import com.example.mal2021_assessment2.model.*;
import com.example.mal2021_assessment2.repository.LMSRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LMSServiceTest {
    @Mock
    private LMSRepository lmsRepository;

    @InjectMocks
    private LMSService lmsService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    // Test 1: Get Active Students
    @Test
    void testGetActiveStudents(){
        // Dummy Data 🧍
        Student student1 = new Student(101L, "Alice Mice", "alice.mice@student.edu.my");
        Student student2 = new Student(102L, "Brian Thomas", "brian.thomas@student.edu.my");

        Enrollment enrollment1 = new Enrollment(101L, "MAL2021");

        when(lmsRepository.getAllStudents()).thenReturn(Arrays.asList(student1, student2));
        when(lmsRepository.getAllEnrollments()).thenReturn(Arrays.asList(enrollment1));

        // Act
        List<Student> result = lmsService.getActiveStudents();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Alice Mice", result.get(0).getName());
    }

    // Test 2: Get most Active Instructor
    @Test
    void testGetMostActiveInstructor(){
        // Dummy data 🧍
        Instructor instructor1 = new Instructor(001L, "Dr. Natalie Ouellette", "natalie.ouellette@college.edu.my");
        Instructor instructor2 = new Instructor(002L, "Prof. Michael Myers", "michael.myers@college.edu.my");

        Course aiCourse = new Course("MAL2019", "Artificial Intelligence", 001L);
        Course softwareCourse = new Course("MAL2021", "Software Development Tools & Practices", 002L);

        Enrollment enrollment1 = new Enrollment(101L, "MAL2019");
        Enrollment enrollment2 = new Enrollment(102L, "MAL2021");
        Enrollment enrollment3 = new Enrollment(103L, "MAL2021");

        when(lmsRepository.getAllInstructors()).thenReturn(Arrays.asList(instructor1, instructor2));
        when(lmsRepository.getAllCourses()).thenReturn(Arrays.asList(aiCourse, softwareCourse));
        when(lmsRepository.getAllEnrollments()).thenReturn(Arrays.asList(enrollment1, enrollment2, enrollment3));

        // Act
        Instructor result = lmsService.getMostActiveInstructor();

        // Assert
        assertEquals("Prof. Michael Myers", result.getName());
    }

    // Test 3: Get Instructors with no enrollments
    @Test
    void testGetInstructorsWithNoEnrollments(){
        Instructor instructor1 = new Instructor(001L, "Dr. Natalie Ouellette", "natalie.ouellette@college.edu.my");
        Instructor instructor2 = new Instructor(002L, "Prof. Michael Myers", "michael.myers@college.edu.my");

        Course aiCourse = new Course("MAL2019", "Artificial Intelligence", 001L);
        Course softwareCourse = new Course("MAL2021", "Software Development Tools & Practices", 001L);

        Enrollment enrollment1 = new Enrollment(101L, "MAL2021");

        when(lmsRepository.getAllInstructors()).thenReturn(Arrays.asList(instructor1, instructor2));
        when(lmsRepository.getAllCourses()).thenReturn(Arrays.asList(aiCourse, softwareCourse));
        when(lmsRepository.getAllEnrollments()).thenReturn(Arrays.asList(enrollment1));

        // Act
        List<Instructor> result = lmsService.getInstructorsWithNoEnrollments();

        // Assert
        assertEquals(1, result.size());
        assertTrue(result.stream().anyMatch(i -> i.getName().equals("Prof. Michael Myers")));
    }

    // Test 4: Empty data
    void testGetMostActiveInstructor_EmptyData(){
        when(lmsRepository.getAllInstructors()).thenReturn(Arrays.asList());
        when(lmsRepository.getAllEnrollments()).thenReturn(Arrays.asList());

        // Act
        Instructor result = lmsService.getMostActiveInstructor();

        // Assert
        assertNull(result);
    }
}
