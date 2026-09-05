
package com.skillbridge.skillbridge_backend.controller;

import com.skillbridge.skillbridge_backend.dto.OpportunityRequest;
import com.skillbridge.skillbridge_backend.dto.OpportunityResponse;
import com.skillbridge.skillbridge_backend.service.OpportunityService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/opportunities")
public class OpportunityController {

    private final OpportunityService opportunityService;

    public OpportunityController(OpportunityService opportunityService) {
        this.opportunityService = opportunityService;
    }

    @GetMapping
    public ResponseEntity<List<OpportunityResponse>> getAllOpportunities() {
        return ResponseEntity.ok(opportunityService.getAllOpportunities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OpportunityResponse> getOpportunityById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                opportunityService.getOpportunityById(id)
        );
    }

    @PostMapping
    public ResponseEntity<OpportunityResponse> createOpportunity(
            @Valid @RequestBody OpportunityRequest request) {

        return ResponseEntity.ok(
                opportunityService.createOpportunity(request)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<OpportunityResponse> updateOpportunity(
            @PathVariable Long id,
            @Valid @RequestBody OpportunityRequest request) {

        return ResponseEntity.ok(
                opportunityService.updateOpportunity(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOpportunity(
            @PathVariable Long id) {

        opportunityService.deleteOpportunity(id);

        return ResponseEntity.noContent().build();
    }
}