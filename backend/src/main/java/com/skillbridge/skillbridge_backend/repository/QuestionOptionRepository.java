package com.skillbridge.skillbridge_backend.repository;

import com.skillbridge.skillbridge_backend.entity.QuestionOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionOptionRepository extends JpaRepository<QuestionOption, Long> {

    List<QuestionOption> findByQuestionQuestionId(Long questionId);
}