package com.example.survey.request;

import com.example.survey.entity.AnswerType;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AnswerSubmission(
    @NotNull
    Long questionId,
    List<Long> choiceIds,
    String text,
    @NotNull
    AnswerType answerType,
    boolean answered,
    boolean required
) {
}
