package com.skillbridge.skillbridge_backend.controller;

import com.skillbridge.skillbridge_backend.dto.StudentProfileRequest;
import com.skillbridge.skillbridge_backend.entity.Student;
import com.skillbridge.skillbridge_backend.service.StudentService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/profile")
    public ResponseEntity<Student> createProfile(
            @Valid @RequestBody StudentProfileRequest request,
            Authentication authentication) {

        Long userId =
                (Long) authentication.getPrincipal();

        Student student =
                studentService.createProfile(userId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(student);
    }

    @GetMapping("/profile")
    public ResponseEntity<Student> getProfile(
            Authentication authentication) {

        Long userId =
                (Long) authentication.getPrincipal();

        Student student =
                studentService.getProfile(userId);

        return ResponseEntity.ok(student);
    }

    @PutMapping("/profile")
    public ResponseEntity<Student> updateProfile(
            @Valid @RequestBody StudentProfileRequest request,
            Authentication authentication) {

        Long userId =
                (Long) authentication.getPrincipal();

        Student student =
                studentService.updateProfile(userId, request);

        return ResponseEntity.ok(student);
    }
}