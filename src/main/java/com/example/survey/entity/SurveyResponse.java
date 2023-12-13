package com.example.survey.entity;

import jakarta.persistence.*;

import java.util.*;

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

  @Column(name = "submission_id", nullable = false)
  private UUID submissionId;

  @Column(nullable = false)
  private String userEmail;

  @OneToMany(mappedBy = "surveyResponse", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserAnswer> userAnswers = new ArrayList<>();

  public SurveyResponse() {
  }

  public SurveyResponse(Survey survey, UUID submissionId, String userEmail, List<UserAnswer> userAnswers) {
    this.survey = survey;
    this.submissionId = submissionId;
    this.userEmail = userEmail;
    if (userAnswers != null) {
      this.addUserAnswers(userAnswers);
    }
  }

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

  public List<UserAnswer> getUserAnswers() {
    return this.userAnswers;
  }

  public void addUserAnswers(List<UserAnswer> answers) {
    answers.forEach(a -> a.setSurveyResponse(this));
    this.userAnswers.addAll(answers);
  }

  public UUID getSubmissionId() {
    return this.submissionId;
  }

  public void setSubmissionId(UUID submissionId) {
    this.submissionId = submissionId;
  }
}
