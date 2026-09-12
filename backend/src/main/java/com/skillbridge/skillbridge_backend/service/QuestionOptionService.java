package com.skillbridge.skillbridge_backend.service;

import com.skillbridge.skillbridge_backend.entity.QuestionOption;
import com.skillbridge.skillbridge_backend.repository.QuestionOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionOptionService {

    private final QuestionOptionRepository questionOptionRepository;

    public QuestionOption createOption(QuestionOption option) {
        return questionOptionRepository.save(option);
    }

    public QuestionOption getOptionById(Long optionId) {
        return questionOptionRepository.findById(optionId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Question option not found with id: " + optionId
                        ));
    }

    public List<QuestionOption> getOptionsByQuestion(Long questionId) {
        return questionOptionRepository.findByQuestionQuestionId(questionId);
    }

    public void deleteOption(Long optionId) {
        if (!questionOptionRepository.existsById(optionId)) {
            throw new RuntimeException(
                    "Question option not found with id: " + optionId
            );
        }

        questionOptionRepository.deleteById(optionId);
    }
}