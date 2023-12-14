package com.example.survey.service;

import com.example.survey.exception.SurveyNotFoundException;
import com.example.survey.mapper.SurveyToSurveyDTOMapper;
import com.example.survey.repository.SurveyRepository;
import com.example.survey.response.SurveyDTO;
import com.example.survey.response.SurveyTitle;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetSurveyService {
  private final SurveyRepository surveyRepository;
  private final SurveyToSurveyDTOMapper surveyToSurveyDTOMapper;

  public GetSurveyService(SurveyRepository surveyRepository, SurveyToSurveyDTOMapper surveyToSurveyDTOMapper) {
    this.surveyRepository = surveyRepository;
    this.surveyToSurveyDTOMapper = surveyToSurveyDTOMapper;
  }

  public SurveyDTO getSurvey(String surveyTitle) {
    var survey = surveyRepository.findByTitle(surveyTitle)
        .orElseThrow(() -> new SurveyNotFoundException("Survey with title %s not found".formatted(surveyTitle)));
    return surveyToSurveyDTOMapper.toSurveyResponseDTO(survey);
  }

  public List<SurveyTitle> getSurveyTitles() {
    return surveyRepository.findAllTitles();
  }
}
