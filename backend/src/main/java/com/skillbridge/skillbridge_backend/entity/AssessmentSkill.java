package com.skillbridge.skillbridge_backend.entity;
import java.math.BigDecimal;
import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "assessment_skills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssessmentSkill {

    @EmbeddedId
    private AssessmentSkillId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("assessmentId")
    @JoinColumn(name = "assessment_id", nullable = false)
    private Assessment assessment;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("skillId")
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @Builder.Default
@Column(nullable = false)
private BigDecimal weight = BigDecimal.ONE;
}