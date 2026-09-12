package com.skillbridge.skillbridge_backend.controller;
import java.math.BigDecimal;
import com.skillbridge.skillbridge_backend.entity.Student;
import com.skillbridge.skillbridge_backend.entity.StudentSkill;
import com.skillbridge.skillbridge_backend.service.StudentService;
import com.skillbridge.skillbridge_backend.service.StudentSkillService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students/skills")
public class StudentSkillController {

    private final StudentSkillService studentSkillService;
    private final StudentService studentService;

    public StudentSkillController(
            StudentSkillService studentSkillService,
            StudentService studentService) {

        this.studentSkillService = studentSkillService;
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentSkill> addSkill(
            @RequestParam Long skillId,
            @RequestParam(required = false) String level,
            @RequestParam(required = false) BigDecimal score,
            Authentication authentication) {

        Long userId = (Long) authentication.getPrincipal();

        Student student = studentService.getProfile(userId);

        StudentSkill studentSkill =
                studentSkillService.addSkill(
                        student.getStudentId(),
                        skillId,
                        level,
                        score
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(studentSkill);
    }

    @GetMapping
    public ResponseEntity<List<StudentSkill>> getMySkills(
            Authentication authentication) {

        Long userId = (Long) authentication.getPrincipal();

        Student student = studentService.getProfile(userId);

        List<StudentSkill> skills =
                studentSkillService.getStudentSkills(
                        student.getStudentId()
                );

        return ResponseEntity.ok(skills);
    }

    @DeleteMapping("/{skillId}")
    public ResponseEntity<Void> removeSkill(
            @PathVariable Long skillId,
            Authentication authentication) {

        Long userId = (Long) authentication.getPrincipal();

        Student student = studentService.getProfile(userId);

        studentSkillService.removeSkill(
                student.getStudentId(),
                skillId
        );

        return ResponseEntity.noContent().build();
    }
}