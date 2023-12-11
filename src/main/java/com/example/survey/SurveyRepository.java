package com.example.survey;

import com.example.survey.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

  @Query("SELECT DISTINCT s FROM Survey s LEFT JOIN FETCH s.questions q LEFT JOIN FETCH q.choices c WHERE s.title = :surveyTitle")
  Optional<Survey> findByTitle(@Param("surveyTitle") String surveyTitle);

  @Query("SELECT DISTINCT new com.example.survey.SurveyTitle(s.title) FROM Survey s")
  List<SurveyTitle> findAllTitles();
}
