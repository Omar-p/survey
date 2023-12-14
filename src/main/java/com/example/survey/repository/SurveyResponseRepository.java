package com.example.survey.repository;

import com.example.survey.entity.SurveyResponse;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface SurveyResponseRepository extends JpaRepository<SurveyResponse, Long> {
  boolean existsBySubmissionId(UUID submissionId);

  @EntityGraph(attributePaths = {"userAnswers", "userAnswers.selectedChoices", "userAnswers.question", "survey.title"})
  Optional<SurveyResponse> findBySubmissionId(UUID submissionId);

  // find all survey responses by survey id
  @EntityGraph(attributePaths = {"userAnswers", "userAnswers.selectedChoices", "userAnswers.question", "survey.title"})
  Optional<SurveyResponse> findBySurveyId(Long surveyId);

  // find all survey responses by survey title
  @EntityGraph(attributePaths = {"userAnswers", "userAnswers.selectedChoices", "userAnswers.question", "survey.title"})
  List<SurveyResponse> findBySurveyTitle(String surveyTitle);
}
