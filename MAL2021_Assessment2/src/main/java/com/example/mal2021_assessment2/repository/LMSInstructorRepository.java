package com.example.mal2021_assessment2.repository;

import com.example.mal2021_assessment2.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LMSInstructorRepository extends JpaRepository<Instructor, Long> {
    Optional<Instructor> findByInstructorId(String instructorId);
}
