package com.example.survey;

import java.util.List;

public record SurveyDTO(
    String title,
    List<QuestionDTO> questions
) {
}
