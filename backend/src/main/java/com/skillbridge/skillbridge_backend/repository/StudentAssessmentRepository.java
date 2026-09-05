package com.skillbridge.skillbridge_backend.repository;

import com.skillbridge.skillbridge_backend.entity.StudentAssessment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentAssessmentRepository
        extends JpaRepository<StudentAssessment, Long> {

    List<StudentAssessment> findByStudentStudentId(Long studentId);

    List<StudentAssessment> findByAssessmentAssessmentId(Long assessmentId);

    Optional<StudentAssessment> findByStudentStudentIdAndAssessmentAssessmentId(
            Long studentId,
            Long assessmentId
    );
}