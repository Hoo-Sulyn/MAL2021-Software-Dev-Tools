package com.example.mal2021_assessment2.service;

import com.example.mal2021_assessment2.model.*;
import com.example.mal2021_assessment2.repository.LMSCourseRepository;
import com.example.mal2021_assessment2.repository.LMSInstructorRepository;
import com.example.mal2021_assessment2.repository.LMSStudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class LMSServiceIntegrationTest {
    @Autowired
    private LMSService lmsService;

    @Autowired
    private LMSCourseRepository courseRepo;

    @Autowired
    private LMSInstructorRepository instructorRepo;

    @Test
    void testRetrieveStudentEnrollmentsAndActiveStudents() {
        // 1. Fetch all students
        List<Student> activeStudents = lmsService.getActiveStudents();
        assertNotNull(activeStudents);
        assertTrue(activeStudents.size() >= 1);

        // 2. Find Alice's real ID from the seeded data
        Long realAliceId = activeStudents.stream()
                .filter(s -> s.getName().equalsIgnoreCase("Alice"))
                .map(Student::getStudentId)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Alice not found in database!"));

        // 3. Use that real ID for the enrollment check
        List<Enrollment> aliceEnrollments = lmsService.getStudentEnrollments(realAliceId);

        assertNotNull(aliceEnrollments);
        assertEquals(2, aliceEnrollments.size(), "Alice should have 2 enrollments");

    }

    @Test
    void testRetrieveMostActiveAndInactiveInstructors() {
        // 1. Test Most Active
        Instructor mostActive = lmsService.getMostActiveInstructor();
        assertNotNull(mostActive);
        assertEquals("Dr. Smith", mostActive.getName());

        // 2. Test Instructors with No Enrollments
        List<Instructor> inactive = lmsService.getInstructorsWithNoEnrollments();
        assertNotNull(inactive);
        assertFalse(inactive.isEmpty(), "Should find at least one instructor with no enrollments");
        assertTrue(inactive.stream().anyMatch(i -> i.getName().equals("Prof. Jones")));
    }

    @Test
    void testStudentToInstructorRelationship() {
        // 1. Get Alice's ID dynamically
        Long aliceId = lmsService.getActiveStudents().stream()
                .filter(s -> s.getName().equals("Alice"))
                .map(Student::getStudentId)
                .findFirst().get();

        // 2. Get her enrollments
        List<Enrollment> enrollments = lmsService.getStudentEnrollments(aliceId);
        String courseId = enrollments.get(0).getCourseId();

        // 3. Find the course and verify it belongs to Dr. Smith (Instructor 501 area)
        Course course = courseRepo.findById(courseId).orElseThrow();
        Instructor instructor = instructorRepo.findById(course.getInstructorId()).orElseThrow();

        assertThat(instructor.getName()).isEqualTo("Dr. Smith");
    }
}
