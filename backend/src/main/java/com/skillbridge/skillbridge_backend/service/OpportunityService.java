package com.skillbridge.skillbridge_backend.service;
import java.math.BigDecimal;
import com.skillbridge.skillbridge_backend.dto.OpportunityRequest;
import com.skillbridge.skillbridge_backend.dto.OpportunityResponse;
import com.skillbridge.skillbridge_backend.dto.SkillResponse;
import com.skillbridge.skillbridge_backend.entity.Company;
import com.skillbridge.skillbridge_backend.entity.Opportunity;
import com.skillbridge.skillbridge_backend.entity.OpportunitySkill;
import com.skillbridge.skillbridge_backend.entity.OpportunitySkillId;
import com.skillbridge.skillbridge_backend.entity.Skill;
import com.skillbridge.skillbridge_backend.repository.CompanyRepository;
import com.skillbridge.skillbridge_backend.repository.OpportunityRepository;
import com.skillbridge.skillbridge_backend.repository.OpportunitySkillRepository;
import com.skillbridge.skillbridge_backend.repository.SkillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OpportunityService {

    private final OpportunityRepository opportunityRepository;
    private final OpportunitySkillRepository opportunitySkillRepository;
    private final CompanyRepository companyRepository;
    private final SkillRepository skillRepository;

    public OpportunityService(
            OpportunityRepository opportunityRepository,
            OpportunitySkillRepository opportunitySkillRepository,
            CompanyRepository companyRepository,
            SkillRepository skillRepository) {
        this.opportunityRepository = opportunityRepository;
        this.opportunitySkillRepository = opportunitySkillRepository;
        this.companyRepository = companyRepository;
        this.skillRepository = skillRepository;
    }

    @Transactional(readOnly = true)
    public List<OpportunityResponse> getAllOpportunities() {
        return opportunityRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public OpportunityResponse getOpportunityById(Long id) {
        Opportunity opportunity = opportunityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Opportunity not found"));

        return toResponse(opportunity);
    }

    @Transactional
    public OpportunityResponse createOpportunity(OpportunityRequest request) {

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        Opportunity opportunity = Opportunity.builder()
                .company(company)
                .title(request.getTitle())
                .description(request.getDescription())
                .opportunityType(request.getOpportunityType())
                .location(request.getLocation())
                .deadline(request.getDeadline())
                .createdAt(OffsetDateTime.now())
                .build();

        opportunity = opportunityRepository.save(opportunity);

        saveOpportunitySkills(opportunity, request.getSkillIds());

        return toResponse(opportunity);
    }

    @Transactional
    public OpportunityResponse updateOpportunity(Long id, OpportunityRequest request) {

        Opportunity opportunity = opportunityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Opportunity not found"));

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        opportunity.setCompany(company);
        opportunity.setTitle(request.getTitle());
        opportunity.setDescription(request.getDescription());
        opportunity.setOpportunityType(request.getOpportunityType());
        opportunity.setLocation(request.getLocation());
        opportunity.setDeadline(request.getDeadline());

        opportunity = opportunityRepository.save(opportunity);

        opportunitySkillRepository.deleteByOpportunityOpportunityId(id);

        saveOpportunitySkills(opportunity, request.getSkillIds());

        return toResponse(opportunity);
    }

    @Transactional
    public void deleteOpportunity(Long id) {

        Opportunity opportunity = opportunityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Opportunity not found"));

        opportunitySkillRepository.deleteByOpportunityOpportunityId(id);
        opportunityRepository.delete(opportunity);
    }

    private void saveOpportunitySkills(
            Opportunity opportunity,
            List<Long> skillIds) {

        if (skillIds == null || skillIds.isEmpty()) {
            return;
        }

        for (Long skillId : skillIds) {

            Skill skill = skillRepository.findById(skillId)
                    .orElseThrow(() ->
                            new RuntimeException("Skill not found: " + skillId));

            OpportunitySkillId opportunitySkillId =
                    new OpportunitySkillId(
                            opportunity.getOpportunityId(),
                            skill.getSkillId()
                    );

            OpportunitySkill opportunitySkill = OpportunitySkill.builder()
                    .id(opportunitySkillId)
                    .opportunity(opportunity)
                    .skill(skill)
                    .requiredLevel("BEGINNER")
                    .weight(BigDecimal.ONE)
                    .build();

            opportunitySkillRepository.save(opportunitySkill);
        }
    }

    private OpportunityResponse toResponse(Opportunity opportunity) {

        OpportunityResponse response = new OpportunityResponse();

        response.setOpportunityId(opportunity.getOpportunityId());
        response.setCompanyId(opportunity.getCompany().getCompanyId());
        response.setCompanyName(opportunity.getCompany().getCompanyName());
        response.setTitle(opportunity.getTitle());
        response.setDescription(opportunity.getDescription());
        response.setOpportunityType(opportunity.getOpportunityType());
        response.setLocation(opportunity.getLocation());
        response.setDeadline(opportunity.getDeadline());

        List<SkillResponse> skills = new ArrayList<>();

        List<OpportunitySkill> opportunitySkills =
                opportunitySkillRepository
                        .findByOpportunityOpportunityId(
                                opportunity.getOpportunityId());

        for (OpportunitySkill opportunitySkill : opportunitySkills) {

            SkillResponse skillResponse = new SkillResponse();

            skillResponse.setSkillId(
                    opportunitySkill.getSkill().getSkillId());

            skillResponse.setSkillName(
                    opportunitySkill.getSkill().getSkillName());

            skillResponse.setRequiredLevel(
                    opportunitySkill.getRequiredLevel());

            skills.add(skillResponse);
        }

        response.setRequiredSkills(skills);

        return response;
    }
}