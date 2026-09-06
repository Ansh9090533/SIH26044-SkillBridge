package com.skillbridge.skillbridge_backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchResponse {

    private Long opportunityId;

    private String companyName;

    private String jobRole;

    private BigDecimal matchPercentage;

    private List<String> matchingSkills;

    private List<String> missingSkills;
}
