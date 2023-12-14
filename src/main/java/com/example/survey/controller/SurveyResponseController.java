package com.example.survey.controller;

import com.example.survey.response.SurveyResponseDTO;
import com.example.survey.service.GetSurveyResponseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/surveys-responses")
public class SurveyResponseController {

  private final GetSurveyResponseService getSurveyResponseService;

  public SurveyResponseController(GetSurveyResponseService getSurveyResponseService) {
    this.getSurveyResponseService = getSurveyResponseService;
  }


  @GetMapping
  public SurveyResponseDTO getSurveyResponsesBySubmissionId(@RequestParam(name = "submissionId", required = false) String submissionId) {
    return getSurveyResponseService.getSurveyResponseBySubmissionId(submissionId);
  }

  @GetMapping("/title/{surveyTitle}")
  public List<SurveyResponseDTO> getSurveyResponsesByTitle(@PathVariable("surveyTitle") String surveyTitle) {
    return getSurveyResponseService.getSurveyResponseBySurveyTitle(surveyTitle);
  }

}
