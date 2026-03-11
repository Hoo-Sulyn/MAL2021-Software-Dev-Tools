package com.example.mal2021_assessment2.repository;

import com.example.mal2021_assessment2.model.*;
import java.util.List;

public interface LMSRepository {
    List<Student> getAllStudents();
    List<Instructor> getAllInstructors();
    List<Course> getAllCourses();
    List<Enrollment> getAllEnrollments();
}
