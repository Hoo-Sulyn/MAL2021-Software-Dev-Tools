package com.example.mal2021_assessment2.repository;

import com.example.mal2021_assessment2.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface LMSEnrollmentRepository extends JpaRepository<Enrollment, Long>{
    List<Enrollment> findByStudentId(Long studentId);
}
