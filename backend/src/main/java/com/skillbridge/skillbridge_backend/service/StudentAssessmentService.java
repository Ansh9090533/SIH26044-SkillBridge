package com.skillbridge.skillbridge_backend.service;

import com.skillbridge.skillbridge_backend.entity.Assessment;
import com.skillbridge.skillbridge_backend.entity.AssessmentStatus;
import com.skillbridge.skillbridge_backend.entity.Student;
import com.skillbridge.skillbridge_backend.entity.StudentAssessment;
import com.skillbridge.skillbridge_backend.repository.AssessmentRepository;
import com.skillbridge.skillbridge_backend.repository.StudentAssessmentRepository;
import com.skillbridge.skillbridge_backend.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentAssessmentService {

    private final StudentAssessmentRepository studentAssessmentRepository;
    private final StudentRepository studentRepository;
    private final AssessmentRepository assessmentRepository;

    public StudentAssessment startAssessment(
            Long studentId,
            Long assessmentId
    ) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found with id: " + studentId
                        ));

        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Assessment not found with id: " + assessmentId
                        ));

        StudentAssessment studentAssessment = StudentAssessment.builder()
                .student(student)
                .assessment(assessment)
                .status(AssessmentStatus.IN_PROGRESS)
                .startedAt(OffsetDateTime.now())
                .build();

        return studentAssessmentRepository.save(studentAssessment);
    }

    public StudentAssessment getAttemptById(Long studentAssessmentId) {
        return studentAssessmentRepository.findById(studentAssessmentId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Assessment attempt not found with id: "
                                        + studentAssessmentId
                        ));
    }

    public List<StudentAssessment> getStudentAttempts(Long studentId) {
        return studentAssessmentRepository
                .findByStudentStudentId(studentId);
    }
}