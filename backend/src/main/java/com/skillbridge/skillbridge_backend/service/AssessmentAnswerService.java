package com.skillbridge.skillbridge_backend.service;

import com.skillbridge.skillbridge_backend.entity.AssessmentAnswer;
import com.skillbridge.skillbridge_backend.entity.Question;
import com.skillbridge.skillbridge_backend.entity.QuestionOption;
import com.skillbridge.skillbridge_backend.entity.StudentAssessment;
import com.skillbridge.skillbridge_backend.repository.AssessmentAnswerRepository;
import com.skillbridge.skillbridge_backend.repository.QuestionOptionRepository;
import com.skillbridge.skillbridge_backend.repository.QuestionRepository;
import com.skillbridge.skillbridge_backend.repository.StudentAssessmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssessmentAnswerService {

    private final AssessmentAnswerRepository assessmentAnswerRepository;
    private final StudentAssessmentRepository studentAssessmentRepository;
    private final QuestionRepository questionRepository;
    private final QuestionOptionRepository questionOptionRepository;

    public AssessmentAnswer submitAnswer(
            Long studentAssessmentId,
            Long questionId,
            Long selectedOptionId,
            String answerText
    ) {
        StudentAssessment studentAssessment =
                studentAssessmentRepository.findById(studentAssessmentId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Assessment attempt not found with id: "
                                                + studentAssessmentId
                                ));

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Question not found with id: " + questionId
                        ));

        // Make sure the question belongs to this assessment
        if (!question.getAssessment().getAssessmentId()
                .equals(studentAssessment.getAssessment().getAssessmentId())) {

            throw new RuntimeException(
                    "Question does not belong to this assessment"
            );
        }

        AssessmentAnswer answer =
                assessmentAnswerRepository
                        .findByStudentAssessmentStudentAssessmentIdAndQuestionQuestionId(
                                studentAssessmentId,
                                questionId
                        )
                        .orElse(
                                AssessmentAnswer.builder()
                                        .studentAssessment(studentAssessment)
                                        .question(question)
                                        .build()
                        );

        // Handle MCQ / TRUE_FALSE
        if (selectedOptionId != null) {

            QuestionOption option =
                    questionOptionRepository.findById(selectedOptionId)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Question option not found with id: "
                                                    + selectedOptionId
                                    ));

            // Make sure option belongs to this question
            if (!option.getQuestion().getQuestionId()
                    .equals(questionId)) {

                throw new RuntimeException(
                        "Selected option does not belong to this question"
                );
            }

            answer.setSelectedOption(option);
        } else {
            answer.setSelectedOption(null);
        }

        // Handle SHORT_ANSWER
        answer.setAnswerText(answerText);

        return assessmentAnswerRepository.save(answer);
    }

    public List<AssessmentAnswer> getAnswers(
            Long studentAssessmentId
    ) {
        return assessmentAnswerRepository
                .findByStudentAssessmentStudentAssessmentId(
                        studentAssessmentId
                );
    }

    public AssessmentAnswer getAnswer(
            Long studentAssessmentId,
            Long questionId
    ) {
        return assessmentAnswerRepository
                .findByStudentAssessmentStudentAssessmentIdAndQuestionQuestionId(
                        studentAssessmentId,
                        questionId
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Answer not found for this question"
                        ));
    }
}