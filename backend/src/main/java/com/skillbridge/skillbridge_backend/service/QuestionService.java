package com.skillbridge.skillbridge_backend.service;

import com.skillbridge.skillbridge_backend.entity.Question;
import com.skillbridge.skillbridge_backend.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    public Question getQuestionById(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Question not found with id: " + questionId
                        ));
    }

    public List<Question> getQuestionsByAssessment(Long assessmentId) {
        return questionRepository.findByAssessmentAssessmentId(assessmentId);
    }

    public void deleteQuestion(Long questionId) {
        if (!questionRepository.existsById(questionId)) {
            throw new RuntimeException(
                    "Question not found with id: " + questionId
            );
        }

        questionRepository.deleteById(questionId);
    }
}