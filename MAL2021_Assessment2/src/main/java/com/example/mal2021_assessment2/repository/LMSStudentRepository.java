package com.example.mal2021_assessment2.repository;

import com.example.mal2021_assessment2.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LMSStudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);
}
