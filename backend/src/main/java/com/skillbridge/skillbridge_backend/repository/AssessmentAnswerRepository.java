package com.skillbridge.skillbridge_backend.repository;

import com.skillbridge.skillbridge_backend.entity.AssessmentAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssessmentAnswerRepository
        extends JpaRepository<AssessmentAnswer, Long> {

    List<AssessmentAnswer> findByStudentAssessmentStudentAssessmentId(
            Long studentAssessmentId
    );

    Optional<AssessmentAnswer> findByStudentAssessmentStudentAssessmentIdAndQuestionQuestionId(
            Long studentAssessmentId,
            Long questionId
    );
}