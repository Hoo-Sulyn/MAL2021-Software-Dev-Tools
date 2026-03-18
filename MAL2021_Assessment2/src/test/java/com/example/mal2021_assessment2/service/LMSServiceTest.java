package com.example.mal2021_assessment2.service;

import com.example.mal2021_assessment2.model.*;

import com.example.mal2021_assessment2.repository.LMSCourseRepository;
import com.example.mal2021_assessment2.repository.LMSEnrollmentRepository;
import com.example.mal2021_assessment2.repository.LMSInstructorRepository;
import com.example.mal2021_assessment2.repository.LMSStudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LMSServiceTest {
    @Mock private LMSCourseRepository courseRepository;
    @Mock private LMSStudentRepository studentRepository;
    @Mock private LMSInstructorRepository instructorRepository;
    @Mock private LMSEnrollmentRepository enrollmentRepository;

    @InjectMocks
    private LMSService lmsService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    // Test 1: Get Student Enrollments
    @Test
    void testGetCoursesForStudent(){
        Long studentId = 101L;
        Course course = new Course("MAL2021", "Software Dev", 1L);
        Enrollment enrollment = new Enrollment(101L, "MAL2021");

        when(enrollmentRepository.findByStudentId(studentId)).thenReturn(Arrays.asList(enrollment));
        when(courseRepository.findAllById(anyList())).thenReturn(Arrays.asList(course));

        // Act
        List<Course> result = lmsService.getCoursesForStudent(studentId);

        // Assert
        assertEquals(1, result.size());
        assertEquals("Software Dev", result.get(0).getTitle());
    }

    // Test 2: Get Active Students
    @Test
    void testGetActiveStudents(){
        // Dummy Data 🧍
        Student student1 = new Student(101L, "Alice Mice", "alice.mice@student.edu.my");
        Student student2 = new Student(102L, "Brian Thomas", "brian.thomas@student.edu.my");

        Enrollment enrollment1 = new Enrollment(101L, "MAL2021");

        when(enrollmentRepository.findAll()).thenReturn(Arrays.asList(enrollment1));
        when(studentRepository.findAllById(anyList())).thenReturn(Arrays.asList(student1));

        // Act
        List<Student> result = lmsService.getActiveStudents();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Alice Mice", result.get(0).getName());
    }

    // Test 3: Get most Active Instructor
    @Test
    void testGetMostActiveInstructor(){
        // Dummy data 🧍
        Instructor instructor1 = new Instructor(1L, "Dr. Natalie Ouellette", "natalie.ouellette@college.edu.my");
        Instructor instructor2 = new Instructor(2L, "Prof. Michael Myers", "michael.myers@college.edu.my");

        Course aiCourse = new Course("MAL2019", "Artificial Intelligence", 1L);
        Course softwareCourse = new Course("MAL2021", "Software Development Tools & Practices", 2L);

        Enrollment enrollment1 = new Enrollment(101L, "MAL2019");
        Enrollment enrollment2 = new Enrollment(102L, "MAL2021");
        Enrollment enrollment3 = new Enrollment(103L, "MAL2021");

        when(enrollmentRepository.findAll()).thenReturn(Arrays.asList(enrollment1, enrollment2, enrollment3));
        when(courseRepository.findAll()).thenReturn(Arrays.asList(aiCourse, softwareCourse));
        when(instructorRepository.findById(2L)).thenReturn(Optional.of(instructor2));

        // Act
        Instructor result = lmsService.getMostActiveInstructor();

        // Assert
        assertEquals("Prof. Michael Myers", result.getName());
    }

    // Test 4: Get Instructors with no enrollments
    @Test
    void testGetInstructorsWithNoEnrollments(){
        Instructor instructor1 = new Instructor(1L, "Dr. Natalie Ouellette", "natalie.ouellette@college.edu.my");
        Instructor instructor2 = new Instructor(2L, "Prof. Michael Myers", "michael.myers@college.edu.my");

        Course softwareCourse = new Course("MAL2021", "Software Developmen", 1L);

        Enrollment enrollment1 = new Enrollment(101L, "MAL2021");

        when(enrollmentRepository.findAll()).thenReturn(Arrays.asList(enrollment1));
        when(courseRepository.findAll()).thenReturn(Arrays.asList(softwareCourse));
        when(instructorRepository.findAll()).thenReturn(Arrays.asList(instructor1, instructor2));

        // Act
        List<Instructor> result = lmsService.getInstructorsWithNoEnrollments();

        // Assert
        assertEquals(1, result.size());
        assertTrue(result.stream().anyMatch(i -> i.getName().equals("Prof. Michael Myers")));
    }

    // Test 5: Empty data
    @Test
    void testGetMostActiveInstructor_EmptyData(){
        when(enrollmentRepository.findAll()).thenReturn(Arrays.asList());
        when(courseRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        Instructor result = lmsService.getMostActiveInstructor();

        // Assert
        assertNull(result);
    }
}
