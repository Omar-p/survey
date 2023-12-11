package com.example.survey.entity;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "question")
public class Question {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String text;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private QuestionType type;

  @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  private Set<Choice> choices = new HashSet<>();

  @Column(nullable = false)
  private boolean required;

  @ManyToOne
  @JoinColumn(name = "survey_id")
  private Survey survey;

  @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserAnswer> userAnswers = new ArrayList<>();

  public Question() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public QuestionType getType() {
    return type;
  }

  public void setType(QuestionType type) {
    this.type = type;
  }

  public boolean isRequired() {
    return required;
  }

  public void setRequired(boolean required) {
    this.required = required;
  }

  public Survey getSurvey() {
    return survey;
  }

  public void setSurvey(Survey survey) {
    this.survey = survey;
  }

  public Set<Choice> getChoices() {
    return choices;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Question question = (Question) o;

    if (required != question.required) return false;
    if (!Objects.equals(id, question.id)) return false;
    if (!Objects.equals(text, question.text)) return false;
    return type == question.type;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (text != null ? text.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    result = 31 * result + (required ? 1 : 0);
    return result;
  }
}
