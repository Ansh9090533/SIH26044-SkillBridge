package com.skillbridge.skillbridge_backend.repository;

import com.skillbridge.skillbridge_backend.entity.AssessmentSkillResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssessmentSkillResultRepository
        extends JpaRepository<AssessmentSkillResult, Long> {

    List<AssessmentSkillResult> findByStudentAssessmentStudentAssessmentId(
            Long studentAssessmentId
    );

    Optional<AssessmentSkillResult>
    findByStudentAssessmentStudentAssessmentIdAndSkillSkillId(
            Long studentAssessmentId,
            Long skillId
    );
}