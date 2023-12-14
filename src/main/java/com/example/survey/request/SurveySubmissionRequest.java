package com.example.survey.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

import java.util.List;
import java.util.UUID;

public record SurveySubmissionRequest(
    @Email
    String email,
    UUID submissionId,

    String surveyTitle,

    @Valid
    List<AnswerSubmission> answers
) {
}
