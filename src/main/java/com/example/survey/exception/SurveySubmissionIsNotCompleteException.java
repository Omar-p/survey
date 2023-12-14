package com.example.survey.exception;

public class SurveySubmissionIsNotCompleteException extends RuntimeException {
  public SurveySubmissionIsNotCompleteException(String message) {
    super(message);
  }
}
