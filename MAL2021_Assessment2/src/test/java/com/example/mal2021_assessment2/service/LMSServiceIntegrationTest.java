package com.example.mal2021_assessment2.service;

import com.example.mal2021_assessment2.model.*;
import com.example.mal2021_assessment2.repository.LMSRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Transactional
public class LMSServiceIntegrationTest {
    @Autowired
    private LMSService lmsService;

//    @Autowired
//    private LMSRepository lmsRepository;

    @Test
    void testActiveStudentIntegration(){
        // Act
        List<Student> activeStudents = lmsService.getActiveStudents();

        // Assert
        assertNotNull(activeStudents);
        assertFalse(activeStudents.isEmpty(), "Service should return students from the real Repository");
    }

    @Test
    void testCourseEnrollmentIntegration(){
        // Act
        Long testStudentId = 101L;
        List<Course> courses = lmsService.getCoursesForStudent(testStudentId);

        // Assert
        assertNotNull(courses);

        // if dummy data link 101 to a course, this will pass
        System.out.println("Integrated courses found: " + courses.size());
    }
}
