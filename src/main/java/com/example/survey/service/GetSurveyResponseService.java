package com.example.survey.service;

import com.example.survey.exception.SurveyResponseNotFoundException;
import com.example.survey.mapper.SurveyResponseMapper;
import com.example.survey.repository.SurveyRepository;
import com.example.survey.repository.SurveyResponseRepository;
import com.example.survey.response.SurveyResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetSurveyResponseService {

  private final SurveyResponseRepository surveyResponseRepository;
  private final SurveyRepository surveyRepository;
  private final SurveyResponseMapper surveyResponseMapper;

  public GetSurveyResponseService(SurveyResponseRepository surveyResponseRepository, SurveyRepository surveyRepository, SurveyResponseMapper surveyResponseMapper) {
    this.surveyResponseRepository = surveyResponseRepository;
    this.surveyRepository = surveyRepository;
    this.surveyResponseMapper = surveyResponseMapper;
  }

  public SurveyResponseDTO getSurveyResponseBySubmissionId(String submissionId) {
    return surveyResponseRepository.findBySubmissionId(UUID.fromString(submissionId))
        .map(surveyResponseMapper::mapToDTO)
        .orElseThrow(() -> new SurveyResponseNotFoundException("No survey response found with submission ID: %s.".formatted(submissionId)));
  }

  public List<SurveyResponseDTO> getSurveyResponseBySurveyTitle(String surveyTitle) {
    boolean isExist = surveyRepository.existsByTitle(surveyTitle);
    if (!isExist) {
      throw new SurveyResponseNotFoundException("No survey found with title: %s.".formatted(surveyTitle));
    }
    return surveyResponseRepository.findBySurveyTitle(surveyTitle)
        .stream()
        .map(surveyResponseMapper::mapToDTO)
        .toList();
  }

}
