package com.example.mal2021_assessment2.repository;

import com.example.mal2021_assessment2.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LMSCourseRepository extends JpaRepository<Course, String> {
    Optional<Course> findById(String courseId);
}
