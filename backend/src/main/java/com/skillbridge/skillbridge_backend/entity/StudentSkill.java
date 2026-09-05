package com.skillbridge.skillbridge_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(
    name = "student_skills",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_id", "skill_id"})
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentSkill {

    @EmbeddedId
    private StudentSkillId id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @MapsId("skillId")
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @Column(name = "level")
    private String level;

    @Column(name = "score")
    private BigDecimal score;
}