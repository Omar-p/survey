package com.example.survey.exception;

public class SurveySubmissionIsAlreadyExistException extends RuntimeException {

  public SurveySubmissionIsAlreadyExistException(String message) {
    super(message);
  }
}
