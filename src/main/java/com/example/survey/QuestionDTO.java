package com.example.survey;

import com.example.survey.entity.QuestionType;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record QuestionDTO(
    Long id,
    String text,
    QuestionType type,
    List<ChoiceDTO> choices,
    boolean required
) {
}
