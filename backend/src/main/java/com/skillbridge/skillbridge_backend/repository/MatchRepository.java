package com.skillbridge.skillbridge_backend.repository;

import com.skillbridge.skillbridge_backend.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {

    List<Match> findByStudentStudentId(Integer studentId);

    List<Match> findByOpportunityOpportunityId(Long opportunityId);
}

