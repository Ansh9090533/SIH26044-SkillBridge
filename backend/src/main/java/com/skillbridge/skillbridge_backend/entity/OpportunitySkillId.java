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
public class OpportunitySkillId implements Serializable {

    private Long opportunityId;
    private Long skillId;
}
