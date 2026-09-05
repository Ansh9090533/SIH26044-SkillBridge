package com.skillbridge.skillbridge_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(
    name = "assessment_skill_results",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_student_assessment_skill",
            columnNames = {"student_assessment_id", "skill_id"}
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssessmentSkillResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id")
    private Long resultId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_assessment_id", nullable = false)
    private StudentAssessment studentAssessment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @Column(nullable = false)
    private BigDecimal score;

    @Enumerated(EnumType.STRING)
    @Column(name = "proficiency")
    private SkillLevel proficiency;
}