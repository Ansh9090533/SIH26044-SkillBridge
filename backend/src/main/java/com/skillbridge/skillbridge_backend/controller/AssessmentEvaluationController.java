package com.skillbridge.skillbridge_backend.controller;

import com.skillbridge.skillbridge_backend.entity.StudentAssessment;
import com.skillbridge.skillbridge_backend.service.AssessmentEvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assessment-evaluation")
@RequiredArgsConstructor
public class AssessmentEvaluationController {

    private final AssessmentEvaluationService assessmentEvaluationService;

    @PostMapping("/{studentAssessmentId}/submit")
    public ResponseEntity<StudentAssessment> submitAssessment(
            @PathVariable Long studentAssessmentId
    ) {
        return ResponseEntity.ok(
                assessmentEvaluationService.evaluateAssessment(
                        studentAssessmentId
                )
        );
    }
}