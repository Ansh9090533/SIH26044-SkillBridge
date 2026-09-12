package com.skillbridge.skillbridge_backend.repository;

import com.skillbridge.skillbridge_backend.entity.AssessmentSkill;
import com.skillbridge.skillbridge_backend.entity.AssessmentSkillId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssessmentSkillRepository
        extends JpaRepository<AssessmentSkill, AssessmentSkillId> {

    List<AssessmentSkill> findByAssessmentAssessmentId(Long assessmentId);

    List<AssessmentSkill> findBySkillSkillId(Long skillId);
}