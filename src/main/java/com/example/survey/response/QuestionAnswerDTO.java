package com.example.survey.response;

import com.example.survey.entity.AnswerType;
import com.example.survey.response.ChoiceDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record QuestionAnswerDTO(
    String questionText,
    String answerText,
    Set<String> choices,
    boolean answered
) {
}
