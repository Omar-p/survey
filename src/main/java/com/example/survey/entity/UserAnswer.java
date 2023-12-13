package com.example.survey.entity;

import jakarta.persistence.*;

import java.util.Collection;
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

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
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

  public UserAnswer() {
  }

  public UserAnswer(Question question, Collection<Choice> selectedChoices, String textAnswer, boolean answered) {
    this.question = question;
    this.textAnswer = textAnswer;
    this.answered = answered;
    if (selectedChoices != null) {
      this.selectedChoices.addAll(selectedChoices);
    }
  }

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

  public void addSelectedChoice(Choice selectedChoice) {
    this.selectedChoices.add(selectedChoice);
  }

  public static UserAnswerBuilder builder() {
    return new UserAnswerBuilder();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UserAnswer that = (UserAnswer) o;

    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }

  public static class UserAnswerBuilder {
    private Question question;
    private boolean answered;
    private Collection<Choice> selectedChoices;

    private String textAnswer;

    public UserAnswerBuilder question(Question question) {
      this.question = question;
      return this;
    }

    public UserAnswerBuilder answered(boolean answered) {
      this.answered = answered;
      return this;
    }

    public UserAnswerBuilder selectedChoices(Collection<Choice> selectedChoices) {
      this.selectedChoices = selectedChoices;
      return this;
    }

    public UserAnswerBuilder textAnswer(String textAnswer) {
      this.textAnswer = textAnswer;
      return this;
    }

    public UserAnswer build() {
      return new UserAnswer(question, selectedChoices, textAnswer, answered);
    }

  }
}