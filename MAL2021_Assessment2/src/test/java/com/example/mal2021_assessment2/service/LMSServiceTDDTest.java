package com.example.mal2021_assessment2.service;

import com.example.mal2021_assessment2.model.*;
import com.example.mal2021_assessment2.repository.LMSRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LMSServiceTDDTest {
    @Mock
    private LMSRepository lmsRepository;

    @InjectMocks
    private LMSService lmsService;

    public LMSServiceTDDTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnCoursesForSpecificStudent(){
        // Red: Retrieve all courses for student 101
        Long studentId = 101L;
        Course softwareDev = new Course("MAL2021", "Software Development Tools & Practices", 1L);
        Enrollment enrollment = new Enrollment(101L, "MAL2021");

        when(lmsRepository.getAllCourses()).thenReturn(Arrays.asList(softwareDev));
        when(lmsRepository.getAllEnrollments()).thenReturn(Arrays.asList(enrollment));

        // Act
        List<Course> result = lmsService.getCoursesForStudent(studentId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Software Development Tools & Practices", result.get(0).getTitle());
        verify(lmsRepository, times(1)).getAllEnrollments();

        // Green: Service code implemented in LMSService.java
    }

    @Test
    void shouldReturnOnlyActiveStudents(){
        // Red: Filter out students with no enrollments
        Student active = new Student(101L, "Alice", "alice@student.edu.my");
        Student inactive = new Student(102L, "Tim", "time@student.edu.my");

        Enrollment enrollment = new Enrollment(101L, "MAL2021");

        when(lmsRepository.getAllStudents()).thenReturn(Arrays.asList(active, inactive));
        when(lmsRepository.getAllEnrollments()).thenReturn(Arrays.asList(enrollment));

        // Act
        List<Student> result = lmsService.getActiveStudents();

        // Assert
        assertEquals(1, result.size(), "Result should only contain 1 active student");
        assertEquals("Alice", result.get(0).getName());
        assertFalse(result.stream().anyMatch(s -> s.getName().equals("Tim")));

        // Green: Minimal service code implemented using Set for ID lookup
    }

    @Test
    void shouldIdentifyMostActiveInstructor(){
        // Red: Find instructor with most student count
        Instructor natalie = new Instructor(1L, "Dr. Natalie Ouellette","natalie.ouellette@college.edu.my");
        Course softwareDev = new Course("MAL2021", "Software Development Tools & Practices", 1L);
        Enrollment enrollment = new Enrollment(101L, "MAL2021");

        when(lmsRepository.getAllInstructors()).thenReturn(Arrays.asList(natalie));
        when(lmsRepository.getAllCourses()).thenReturn(Arrays.asList(softwareDev));
        when(lmsRepository.getAllEnrollments()).thenReturn(Arrays.asList(enrollment));

        // Act
        Instructor result = lmsService.getMostActiveInstructor();

        // Assert
        assertNotNull(result);
        assertEquals("Dr. Natalie Ouellette", result.getName());

        // Green: Minimal service code implemented
    }

    @Test
    void shouldIndentifyIntructorsWithNoEnrollments(){
        // Red: Find instructors with no enrollments
        Instructor active = new Instructor(1L, "Dr. Active", "active@college.edu.my");
        Instructor inactive = new Instructor(2L, "Prof. Inactive", "inactive@college.edu.my");

        Course c1 = new Course("MAL2021", "Software Dev", 1L);
        Course c2 = new Course("MAL2019", "AI", 2L);

        Enrollment enrollment = new Enrollment(1L, "MAL2021");

        when(lmsRepository.getAllInstructors()).thenReturn(Arrays.asList(active, inactive));
        when(lmsRepository.getAllCourses()).thenReturn(Arrays.asList(c1, c2));
        when(lmsRepository.getAllEnrollments()).thenReturn(Arrays.asList(enrollment));

        // Act
        List<Instructor> result = lmsService.getInstructorsWithNoEnrollments();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size(), "Should only find 1 inactive instructor");
        assertEquals("Prof. Inactive", result.get(0).getName());

        // Green Minimal service code implemented using Set difference logic
    }
}
