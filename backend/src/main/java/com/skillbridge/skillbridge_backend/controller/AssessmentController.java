package com.skillbridge.skillbridge_backend.controller;

import com.skillbridge.skillbridge_backend.entity.Assessment;
import com.skillbridge.skillbridge_backend.service.AssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assessments")
@RequiredArgsConstructor
public class AssessmentController {

    private final AssessmentService assessmentService;

    @PostMapping
    public ResponseEntity<Assessment> createAssessment(
            @RequestBody Assessment assessment
    ) {
        return ResponseEntity.ok(
                assessmentService.createAssessment(assessment)
        );
    }

    @GetMapping("/{assessmentId}")
    public ResponseEntity<Assessment> getAssessment(
            @PathVariable Long assessmentId
    ) {
        return ResponseEntity.ok(
                assessmentService.getAssessmentById(assessmentId)
        );
    }

    @GetMapping
    public ResponseEntity<List<Assessment>> getAllAssessments() {
        return ResponseEntity.ok(
                assessmentService.getAllAssessments()
        );
    }
}