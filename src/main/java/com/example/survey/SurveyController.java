package com.example.survey;

import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/surveys")
public class SurveyController {

  private final GetSurveyService getSurveyService;

  public SurveyController(GetSurveyService getSurveyService) {
    this.getSurveyService = getSurveyService;
  }

  @GetMapping
  public SurveyDTO getSurvey(@Param("surveyTitle") String surveyTitle) {
    return getSurveyService.getSurvey(surveyTitle);
  }

  @GetMapping("/titles")
  public List<SurveyTitle> getSurveyTitles() {
    return getSurveyService.getSurveyTitles();
  }

}
