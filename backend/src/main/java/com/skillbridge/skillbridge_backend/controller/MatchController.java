package com.skillbridge.skillbridge_backend.controller;

import com.skillbridge.skillbridge_backend.dto.MatchResponse;
import com.skillbridge.skillbridge_backend.service.MatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<MatchResponse>> getStudentMatches(
            @PathVariable Integer studentId
    ) {
        return ResponseEntity.ok(
                matchService.findMatches(studentId)
        );
    }
}
