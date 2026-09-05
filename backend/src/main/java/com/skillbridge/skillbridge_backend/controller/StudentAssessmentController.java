package com.skillbridge.skillbridge_backend.controller;

import com.skillbridge.skillbridge_backend.entity.StudentAssessment;
import com.skillbridge.skillbridge_backend.service.StudentAssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student-assessments")
@RequiredArgsConstructor
public class StudentAssessmentController {

    private final StudentAssessmentService studentAssessmentService;

    @PostMapping("/start")
    public ResponseEntity<StudentAssessment> startAssessment(
            @RequestParam Long studentId,
            @RequestParam Long assessmentId
    ) {
        return ResponseEntity.ok(
                studentAssessmentService.startAssessment(
                        studentId,
                        assessmentId
                )
        );
    }

    @GetMapping("/{studentAssessmentId}")
    public ResponseEntity<StudentAssessment> getAttempt(
            @PathVariable Long studentAssessmentId
    ) {
        return ResponseEntity.ok(
                studentAssessmentService.getAttemptById(
                        studentAssessmentId
                )
        );
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<StudentAssessment>> getStudentAttempts(
            @PathVariable Long studentId
    ) {
        return ResponseEntity.ok(
                studentAssessmentService.getStudentAttempts(studentId)
        );
    }
}