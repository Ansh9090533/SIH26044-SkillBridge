package com.skillbridge.skillbridge_backend.service;

import com.skillbridge.skillbridge_backend.entity.AssessmentSkillResult;
import com.skillbridge.skillbridge_backend.repository.AssessmentSkillResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssessmentSkillResultService {

    private final AssessmentSkillResultRepository repository;

    public AssessmentSkillResult saveResult(
            AssessmentSkillResult result
    ) {
        return repository.save(result);
    }

    public List<AssessmentSkillResult> getResults(
            Long studentAssessmentId
    ) {
        return repository
                .findByStudentAssessmentStudentAssessmentId(
                        studentAssessmentId
                );
    }

    public AssessmentSkillResult getResult(
            Long studentAssessmentId,
            Long skillId
    ) {
        return repository
                .findByStudentAssessmentStudentAssessmentIdAndSkillSkillId(
                        studentAssessmentId,
                        skillId
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Skill result not found"
                        ));
    }
}