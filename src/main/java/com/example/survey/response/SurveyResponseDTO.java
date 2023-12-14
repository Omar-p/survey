package com.example.survey.response;

import java.util.Set;

public record SurveyResponseDTO(
    String surveyTitle,

    Set<QuestionAnswerDTO> userAnswers
) {
}
