package com.skillbridge.skillbridge_backend.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AssessmentSkillId implements Serializable {

    private Long assessmentId;
    private Long skillId;
}