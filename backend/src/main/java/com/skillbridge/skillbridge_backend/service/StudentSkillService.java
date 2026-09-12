package com.skillbridge.skillbridge_backend.service;

import com.skillbridge.skillbridge_backend.entity.Skill;
import com.skillbridge.skillbridge_backend.entity.Student;
import com.skillbridge.skillbridge_backend.entity.StudentSkill;
import com.skillbridge.skillbridge_backend.entity.StudentSkillId;
import com.skillbridge.skillbridge_backend.repository.SkillRepository;
import com.skillbridge.skillbridge_backend.repository.StudentRepository;
import com.skillbridge.skillbridge_backend.repository.StudentSkillRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class StudentSkillService {

    private final StudentSkillRepository studentSkillRepository;
    private final StudentRepository studentRepository;
    private final SkillRepository skillRepository;

    public StudentSkillService(
            StudentSkillRepository studentSkillRepository,
            StudentRepository studentRepository,
            SkillRepository skillRepository) {

        this.studentSkillRepository = studentSkillRepository;
        this.studentRepository = studentRepository;
        this.skillRepository = skillRepository;
    }

        public StudentSkill addSkill(
        Integer studentId,
        Long skillId,
        String level,
        BigDecimal score) {

        Student student = studentRepository.findById(studentId.longValue())
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() ->
                        new RuntimeException("Skill not found"));

        if (studentSkillRepository
                .existsByStudent_StudentIdAndSkill_SkillId(
                        studentId, skillId)) {

            throw new RuntimeException(
                    "Skill already added to student");
        }

        StudentSkill studentSkill = StudentSkill.builder()
        .id(new StudentSkillId(studentId, skillId))
                .student(student)
                .skill(skill)
                .level(level)
                .score(score)
                .build();

        return studentSkillRepository.save(studentSkill);
    }

    public List<StudentSkill> getStudentSkills(Integer studentId) {

        return studentSkillRepository
                .findByStudent_StudentId(studentId);
    }

    public void removeSkill(
            Integer studentId,
            Long skillId) {

        StudentSkillId id =
        new StudentSkillId(
                studentId,
                skillId);

        if (!studentSkillRepository.existsById(id)) {
            throw new RuntimeException(
                    "Student skill not found");
        }

        studentSkillRepository.deleteById(id);
    }
}