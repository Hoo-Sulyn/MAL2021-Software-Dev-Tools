package com.example.mal2021_assessment2.util;

import com.example.mal2021_assessment2.model.Course;
import com.example.mal2021_assessment2.model.Enrollment;
import com.example.mal2021_assessment2.model.Instructor;
import com.example.mal2021_assessment2.model.Student;
import com.example.mal2021_assessment2.repository.LMSCourseRepository;
import com.example.mal2021_assessment2.repository.LMSEnrollmentRepository;
import com.example.mal2021_assessment2.repository.LMSInstructorRepository;
import com.example.mal2021_assessment2.repository.LMSStudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {
    @Autowired
    private LMSStudentRepository studentRepo;

    @Autowired
    private LMSCourseRepository courseRepo;

    @Autowired
    private LMSInstructorRepository instructorRepo;

    @Autowired
    private LMSEnrollmentRepository enrollmentRepo;

    @Override
    public void run(String... args) throws Exception {
        // 1. Seed Students & capture them
        Student alice = studentRepo.save(new Student(null, "Alice", "alice@student.edu.my"));
        Student tim = studentRepo.save(new Student(null, "Tim", "tim@student.edu.my"));

        // 2. Seed Instructors & capture them
        Instructor smith = instructorRepo.save(new Instructor(null, "Dr. Smith", "smith@instructor.edu.my"));
        Instructor jones = instructorRepo.save(new Instructor(null, "Prof. Jones", "jones@instructor.edu.my"));

        // 3. Seed Courses (Use the ID smith actually got from the database)
        Course ai = courseRepo.save(new Course("MAL2019", "Artificial Intelligence", smith.getInstructorId()));
        Course sd = courseRepo.save(new Course("MAL2021", "Software Development", smith.getInstructorId()));

        // 4. Seed Enrollments (Use the ID alice actually got from the database)
        enrollmentRepo.save(new Enrollment(alice.getStudentId(), ai.getCourseId()));
        enrollmentRepo.save(new Enrollment(alice.getStudentId(), sd.getCourseId()));

        System.out.println("✅ Database has been seeded with dynamic IDs!");
    }
}
