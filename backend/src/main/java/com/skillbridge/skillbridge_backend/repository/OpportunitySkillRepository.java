package com.skillbridge.skillbridge_backend.repository;

import com.skillbridge.skillbridge_backend.entity.OpportunitySkill;
import com.skillbridge.skillbridge_backend.entity.OpportunitySkillId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpportunitySkillRepository
        extends JpaRepository<OpportunitySkill, OpportunitySkillId> {

    List<OpportunitySkill> findByOpportunityOpportunityId(Long opportunityId);

    void deleteByOpportunityOpportunityId(Long opportunityId);
}
