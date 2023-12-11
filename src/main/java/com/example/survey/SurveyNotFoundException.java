package com.example.survey;

public class SurveyNotFoundException extends RuntimeException {

  public SurveyNotFoundException(String message) {
    super(message);
  }
}
