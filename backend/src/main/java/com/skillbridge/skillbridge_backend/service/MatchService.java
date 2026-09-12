package com.skillbridge.skillbridge_backend.service;
import com.skillbridge.skillbridge_backend.entity.AssessmentStatus;
import com.skillbridge.skillbridge_backend.dto.MatchResponse;
import com.skillbridge.skillbridge_backend.entity.AssessmentSkillResult;
import com.skillbridge.skillbridge_backend.entity.Opportunity;
import com.skillbridge.skillbridge_backend.entity.OpportunitySkill;
import com.skillbridge.skillbridge_backend.entity.StudentAssessment;
import com.skillbridge.skillbridge_backend.repository.AssessmentSkillResultRepository;
import com.skillbridge.skillbridge_backend.repository.OpportunityRepository;
import com.skillbridge.skillbridge_backend.repository.OpportunitySkillRepository;
import com.skillbridge.skillbridge_backend.repository.StudentAssessmentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatchService {

    private final StudentAssessmentRepository studentAssessmentRepository;
    private final AssessmentSkillResultRepository assessmentSkillResultRepository;
    private final OpportunityRepository opportunityRepository;
    private final OpportunitySkillRepository opportunitySkillRepository;

    public MatchService(
            StudentAssessmentRepository studentAssessmentRepository,
            AssessmentSkillResultRepository assessmentSkillResultRepository,
            OpportunityRepository opportunityRepository,
            OpportunitySkillRepository opportunitySkillRepository
    ) {
        this.studentAssessmentRepository = studentAssessmentRepository;
        this.assessmentSkillResultRepository = assessmentSkillResultRepository;
        this.opportunityRepository = opportunityRepository;
        this.opportunitySkillRepository = opportunitySkillRepository;
    }

    public List<MatchResponse> findMatches(Integer studentId) {

        List<StudentAssessment> assessments =
                studentAssessmentRepository.findByStudentStudentId(studentId.longValue());

        Optional<StudentAssessment> completedAssessment = assessments.stream()
                .filter(assessment ->
        assessment.getStatus() == AssessmentStatus.COMPLETED)
                .max(Comparator.comparing(
                        StudentAssessment::getCompletedAt,
                        Comparator.nullsLast(Comparator.naturalOrder())
                ));

        if (completedAssessment.isEmpty()) {
            return Collections.emptyList();
        }

        Long assessmentId =
                completedAssessment.get().getStudentAssessmentId();

        List<AssessmentSkillResult> skillResults =
                assessmentSkillResultRepository
                        .findByStudentAssessmentStudentAssessmentId(assessmentId);

        Map<Long, AssessmentSkillResult> studentSkills = skillResults.stream()
                .collect(Collectors.toMap(
                        result -> result.getSkill().getSkillId(),
                        result -> result,
                        (first, second) -> second
                ));

        List<Opportunity> opportunities =
                opportunityRepository.findAll();

        List<MatchResponse> matches = new ArrayList<>();

        for (Opportunity opportunity : opportunities) {

            List<OpportunitySkill> requiredSkills =
                    opportunitySkillRepository
                            .findByOpportunityOpportunityId(
                                    opportunity.getOpportunityId()
                            );

            if (requiredSkills.isEmpty()) {
                continue;
            }

            List<String> matchingSkills = new ArrayList<>();
            List<String> missingSkills = new ArrayList<>();

            BigDecimal totalScore = BigDecimal.ZERO;

            for (OpportunitySkill requiredSkill : requiredSkills) {

                Long skillId =
                        requiredSkill.getSkill().getSkillId();

                AssessmentSkillResult studentSkill =
                        studentSkills.get(skillId);

                String skillName =
                        requiredSkill.getSkill().getSkillName();

                if (studentSkill == null) {
                    missingSkills.add(skillName);
                } else {
                    matchingSkills.add(skillName);

                    BigDecimal score = studentSkill.getScore();

                    if (score != null) {
                        totalScore = totalScore.add(score);
                    }
                }
            }

            BigDecimal matchPercentage = totalScore
                    .divide(
                            BigDecimal.valueOf(requiredSkills.size()),
                            2,
                            RoundingMode.HALF_UP
                    );

            matches.add(
                    MatchResponse.builder()
                            .opportunityId(opportunity.getOpportunityId())
                            .companyName(
                                    opportunity.getCompany()
                                            .getCompanyName()
                            )
                            .jobRole(opportunity.getTitle())
                            .matchPercentage(matchPercentage)
                            .matchingSkills(matchingSkills)
                            .missingSkills(missingSkills)
                            .build()
            );
        }

        matches.sort(
                Comparator.comparing(
                        MatchResponse::getMatchPercentage,
                        Comparator.nullsLast(Comparator.reverseOrder())
                )
        );

        return matches;
    }
}