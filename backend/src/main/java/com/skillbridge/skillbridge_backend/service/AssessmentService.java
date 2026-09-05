package com.skillbridge.skillbridge_backend.service;

import com.skillbridge.skillbridge_backend.entity.Assessment;
import com.skillbridge.skillbridge_backend.repository.AssessmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssessmentService {

    private final AssessmentRepository assessmentRepository;

    public Assessment createAssessment(Assessment assessment) {
        return assessmentRepository.save(assessment);
    }

    public Assessment getAssessmentById(Long assessmentId) {
        return assessmentRepository.findById(assessmentId)
                .orElseThrow(() ->
                        new RuntimeException("Assessment not found with id: " + assessmentId));
    }

    public List<Assessment> getAllAssessments() {
        return assessmentRepository.findAll();
    }
}