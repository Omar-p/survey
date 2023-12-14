package com.example.survey.controller;

import com.example.survey.request.SurveySubmissionRequest;
import com.example.survey.response.SurveyDTO;
import com.example.survey.response.SurveyTitle;
import com.example.survey.service.GetSurveyService;
import com.example.survey.service.SubmitSurveyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/surveys")
public class SurveyController {

  private final GetSurveyService getSurveyService;
  private final SubmitSurveyService submitSurveyService;

  public SurveyController(GetSurveyService getSurveyService, SubmitSurveyService submitSurveyService) {
    this.getSurveyService = getSurveyService;
    this.submitSurveyService = submitSurveyService;
  }

  @GetMapping
  public SurveyDTO getSurvey(@RequestParam(name = "surveyTitle") String surveyTitle) {
    return getSurveyService.getSurvey(surveyTitle);
  }

  @GetMapping("/titles")
  public List<SurveyTitle> getSurveyTitles() {
    return getSurveyService.getSurveyTitles();
  }


  @PostMapping
  public ResponseEntity<?> submitSurvey(@Valid @RequestBody SurveySubmissionRequest surveySubmissionRequest) {
    UUID submissionId = submitSurveyService.submitSurvey(surveySubmissionRequest);
    return ResponseEntity
        .created(UriComponentsBuilder.fromPath("/api/surveys-responses/{submissionId}")
            .buildAndExpand(submissionId, submissionId)
            .toUri())
        .build();
  }
}
