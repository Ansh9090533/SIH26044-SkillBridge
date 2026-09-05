package com.skillbridge.skillbridge_backend.dto;

import java.time.LocalDate;
import java.util.List;

public class OpportunityResponse {

    private Long opportunityId;
    private Long companyId;
    private String companyName;
    private String title;
    private String description;
    private String opportunityType;
    private String location;
    private LocalDate deadline;
    private List<SkillResponse> requiredSkills;

    public Long getOpportunityId() {
        return opportunityId;
    }

    public void setOpportunityId(Long opportunityId) {
        this.opportunityId = opportunityId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOpportunityType() {
        return opportunityType;
    }

    public void setOpportunityType(String opportunityType) {
        this.opportunityType = opportunityType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public List<SkillResponse> getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(List<SkillResponse> requiredSkills) {
        this.requiredSkills = requiredSkills;
    }
}