package com.skillbridge.skillbridge_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "opportunity_skills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpportunitySkill {

    @EmbeddedId
    private OpportunitySkillId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("opportunityId")
    @JoinColumn(name = "opportunity_id", nullable = false)
    private Opportunity opportunity;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("skillId")
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @Column(name = "required_level")
    private String requiredLevel;

    @Column
  private BigDecimal weight;
}