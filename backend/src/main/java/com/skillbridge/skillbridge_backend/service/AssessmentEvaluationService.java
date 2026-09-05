package com.skillbridge.skillbridge_backend.service;

import com.skillbridge.skillbridge_backend.entity.AssessmentAnswer;
import com.skillbridge.skillbridge_backend.entity.AssessmentSkillResult;
import com.skillbridge.skillbridge_backend.entity.AssessmentStatus;
import com.skillbridge.skillbridge_backend.entity.QuestionType;
import com.skillbridge.skillbridge_backend.entity.Skill;
import com.skillbridge.skillbridge_backend.entity.SkillLevel;
import com.skillbridge.skillbridge_backend.entity.StudentAssessment;
import com.skillbridge.skillbridge_backend.repository.AssessmentAnswerRepository;
import com.skillbridge.skillbridge_backend.repository.AssessmentSkillResultRepository;
import com.skillbridge.skillbridge_backend.repository.StudentAssessmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AssessmentEvaluationService {

    private final StudentAssessmentRepository studentAssessmentRepository;
    private final AssessmentAnswerRepository assessmentAnswerRepository;
    private final AssessmentSkillResultRepository assessmentSkillResultRepository;

    @Transactional
    public StudentAssessment evaluateAssessment(Long studentAssessmentId) {

        StudentAssessment studentAssessment =
                studentAssessmentRepository.findById(studentAssessmentId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Assessment attempt not found with id: "
                                                + studentAssessmentId
                                ));

        if (studentAssessment.getStatus() == AssessmentStatus.COMPLETED) {
            throw new RuntimeException(
                    "Assessment has already been evaluated"
            );
        }

        List<AssessmentAnswer> answers =
                assessmentAnswerRepository
                        .findByStudentAssessmentStudentAssessmentId(
                                studentAssessmentId
                        );

        BigDecimal totalMarks = BigDecimal.ZERO;
        BigDecimal obtainedMarks = BigDecimal.ZERO;

        // Stores total marks and obtained marks separately for each skill
        Map<Long, BigDecimal> skillTotalMarks = new HashMap<>();
        Map<Long, BigDecimal> skillObtainedMarks = new HashMap<>();
        Map<Long, Skill> skills = new HashMap<>();

        for (AssessmentAnswer answer : answers) {

            BigDecimal questionMarks =
                    BigDecimal.valueOf(answer.getQuestion().getMarks());

            totalMarks = totalMarks.add(questionMarks);

            boolean correct = false;

            if (answer.getQuestion().getQuestionType() == QuestionType.MCQ
                    || answer.getQuestion().getQuestionType()
                    == QuestionType.TRUE_FALSE) {

                if (answer.getSelectedOption() != null
                        && Boolean.TRUE.equals(
                        answer.getSelectedOption().getIsCorrect())) {

                    correct = true;
                }
            }

            if (correct) {
                answer.setIsCorrect(true);
                answer.setMarksObtained(questionMarks);
                obtainedMarks = obtainedMarks.add(questionMarks);
            } else {
                answer.setIsCorrect(false);
                answer.setMarksObtained(BigDecimal.ZERO);
            }

            assessmentAnswerRepository.save(answer);

            // Skill-wise calculation
            Skill skill = answer.getQuestion().getSkill();

            if (skill != null) {

                Long skillId = skill.getSkillId();

                skills.put(skillId, skill);

                skillTotalMarks.merge(
                        skillId,
                        questionMarks,
                        BigDecimal::add
                );

                BigDecimal marksObtained =
                        correct ? questionMarks : BigDecimal.ZERO;

                skillObtainedMarks.merge(
                        skillId,
                        marksObtained,
                        BigDecimal::add
                );
            }
        }

        // Create/update skill-wise results
        for (Long skillId : skills.keySet()) {

            BigDecimal totalSkillMarks =
                    skillTotalMarks.getOrDefault(
                            skillId,
                            BigDecimal.ZERO
                    );

            BigDecimal obtainedSkillMarks =
                    skillObtainedMarks.getOrDefault(
                            skillId,
                            BigDecimal.ZERO
                    );

            BigDecimal skillPercentage = BigDecimal.ZERO;

            if (totalSkillMarks.compareTo(BigDecimal.ZERO) > 0) {

                skillPercentage =
                        obtainedSkillMarks
                                .multiply(BigDecimal.valueOf(100))
                                .divide(
                                        totalSkillMarks,
                                        2,
                                        RoundingMode.HALF_UP
                                );
            }

            AssessmentSkillResult result =
                    assessmentSkillResultRepository
                            .findByStudentAssessmentStudentAssessmentIdAndSkillSkillId(
                                    studentAssessmentId,
                                    skillId
                            )
                            .orElse(
                                    AssessmentSkillResult.builder()
                                            .studentAssessment(studentAssessment)
                                            .skill(skills.get(skillId))
                                            .build()
                            );

            result.setScore(skillPercentage);
            result.setProficiency(
                    calculateProficiency(skillPercentage)
            );

            assessmentSkillResultRepository.save(result);
        }

        // Overall assessment percentage
        BigDecimal percentage = BigDecimal.ZERO;

        if (totalMarks.compareTo(BigDecimal.ZERO) > 0) {

            percentage =
                    obtainedMarks
                            .multiply(BigDecimal.valueOf(100))
                            .divide(
                                    totalMarks,
                                    2,
                                    RoundingMode.HALF_UP
                            );
        }

        studentAssessment.setScore(obtainedMarks);
        studentAssessment.setPercentage(percentage);
        studentAssessment.setStatus(AssessmentStatus.COMPLETED);
        studentAssessment.setSubmittedAt(OffsetDateTime.now());
        studentAssessment.setCompletedAt(OffsetDateTime.now());

        return studentAssessmentRepository.save(studentAssessment);
    }

    private SkillLevel calculateProficiency(BigDecimal percentage) {

        if (percentage.compareTo(BigDecimal.valueOf(70)) >= 0) {
            return SkillLevel.ADVANCED;
        }

        if (percentage.compareTo(BigDecimal.valueOf(40)) >= 0) {
            return SkillLevel.INTERMEDIATE;
        }

        return SkillLevel.BEGINNER;
    }
}