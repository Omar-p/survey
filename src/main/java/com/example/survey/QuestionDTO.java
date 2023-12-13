package com.example.survey;

import com.example.survey.entity.AnswerType;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record QuestionDTO(
    Long id,
    String text,
    AnswerType type,
    List<ChoiceDTO> choices,
    boolean required
) {
}
