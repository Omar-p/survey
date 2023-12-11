package com.example.survey;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetSurveyService {
  private final SurveyRepository surveyRepository;
  private final SurveyToSurveyDTOMapper surveyToSurveyDTOMapper;

  public GetSurveyService(SurveyRepository surveyRepository, SurveyToSurveyDTOMapper surveyToSurveyDTOMapper) {
    this.surveyRepository = surveyRepository;
    this.surveyToSurveyDTOMapper = surveyToSurveyDTOMapper;
  }

  SurveyDTO getSurvey(String surveyTitle) {
    var survey = surveyRepository.findByTitle(surveyTitle)
        .orElseThrow(() -> new SurveyNotFoundException("Survey with title %s not found".formatted(surveyTitle)));
    return surveyToSurveyDTOMapper.toSurveyResponseDTO(survey);
  }


  List<SurveyTitle> getSurveyTitles() {
    return surveyRepository.findAllTitles();
  }
}
