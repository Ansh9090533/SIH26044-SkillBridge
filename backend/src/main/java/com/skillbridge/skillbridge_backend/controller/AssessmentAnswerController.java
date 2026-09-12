package com.skillbridge.skillbridge_backend.controller;

import com.skillbridge.skillbridge_backend.entity.AssessmentAnswer;
import com.skillbridge.skillbridge_backend.service.AssessmentAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assessment-answers")
@RequiredArgsConstructor
public class AssessmentAnswerController {

    private final AssessmentAnswerService assessmentAnswerService;

    @PostMapping
    public ResponseEntity<AssessmentAnswer> submitAnswer(
            @RequestParam Long studentAssessmentId,
            @RequestParam Long questionId,
            @RequestParam(required = false) Long selectedOptionId,
            @RequestParam(required = false) String answerText
    ) {
        return ResponseEntity.ok(
                assessmentAnswerService.submitAnswer(
                        studentAssessmentId,
                        questionId,
                        selectedOptionId,
                        answerText
                )
        );
    }

    @GetMapping("/{studentAssessmentId}")
    public ResponseEntity<List<AssessmentAnswer>> getAnswers(
            @PathVariable Long studentAssessmentId
    ) {
        return ResponseEntity.ok(
                assessmentAnswerService.getAnswers(studentAssessmentId)
        );
    }

    @GetMapping("/{studentAssessmentId}/question/{questionId}")
    public ResponseEntity<AssessmentAnswer> getAnswer(
            @PathVariable Long studentAssessmentId,
            @PathVariable Long questionId
    ) {
        return ResponseEntity.ok(
                assessmentAnswerService.getAnswer(
                        studentAssessmentId,
                        questionId
                )
        );
    }
}