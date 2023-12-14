package com.example.survey.exception;

public class SurveyResponseNotFoundException extends RuntimeException {
  public SurveyResponseNotFoundException(String message) {
    super(message);
  }
}
