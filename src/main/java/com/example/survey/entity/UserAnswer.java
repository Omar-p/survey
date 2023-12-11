package com.example.survey.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user_answer")
public class UserAnswer {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_answer_id_seq")
  @SequenceGenerator(
      name = "user_answer_id_seq",
      allocationSize = 5
  )
  private Long id;

  @ManyToOne
  @JoinColumn(name = "survey_response_id")
  private SurveyResponse surveyResponse;

  @ManyToOne
  @JoinColumn(name = "question_id")
  private Question question;

  @ManyToMany
  @JoinTable(
      name = "user_answer_choice",
      joinColumns = @JoinColumn(name = "user_answer_id"),
      inverseJoinColumns = @JoinColumn(name = "choice_id")
  )
  private Set<Choice> selectedChoices = new HashSet<>();

  @Column
  private String textAnswer;

  @Column(nullable = false)
  private boolean answered;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public SurveyResponse getSurveyResponse() {
    return surveyResponse;
  }

  public void setSurveyResponse(SurveyResponse surveyResponse) {
    this.surveyResponse = surveyResponse;
  }

  public Question getQuestion() {
    return question;
  }

  public void setQuestion(Question question) {
    this.question = question;
  }

  public String getTextAnswer() {
    return textAnswer;
  }

  public void setTextAnswer(String textAnswer) {
    this.textAnswer = textAnswer;
  }

  public boolean isAnswered() {
    return answered;
  }

  public void setAnswered(boolean answered) {
    this.answered = answered;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UserAnswer that = (UserAnswer) o;

    if (answered != that.answered) return false;
    if (!Objects.equals(id, that.id)) return false;
    return Objects.equals(textAnswer, that.textAnswer);
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (textAnswer != null ? textAnswer.hashCode() : 0);
    result = 31 * result + (answered ? 1 : 0);
    return result;
  }
}