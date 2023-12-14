package com.example.survey.response;

import java.util.List;

public record SurveyDTO(
    String title,
    List<QuestionDTO> questions
) {
}
