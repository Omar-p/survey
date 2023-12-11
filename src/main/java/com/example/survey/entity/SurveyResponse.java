package com.example.survey.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "survey_response")
public class SurveyResponse {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "survey_response_id_seq")
  @SequenceGenerator(
      name = "survey_response_id_seq",
      allocationSize = 5
  )
  private Long id;

  @ManyToOne
  @JoinColumn(name = "survey_id")
  private Survey survey;

  @Column(nullable = false)
  private String userEmail;

  @OneToMany(mappedBy = "surveyResponse", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserAnswer> userAnswers = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Survey getSurvey() {
    return survey;
  }

  public void setSurvey(Survey survey) {
    this.survey = survey;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }
}
