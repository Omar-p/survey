package com.example.survey.entity;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(
    name = "survey", uniqueConstraints = {
    @UniqueConstraint(columnNames = "title")
})
public class Survey {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "survey_id_seq")
  @SequenceGenerator(
      name = "survey_id_seq",
      allocationSize = 5
  )
  private Long id;

  @Column(nullable = false)
  private String title;

  @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Question> questions = new TreeSet<>(Comparator.comparing(Question::getId));

  @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<SurveyResponse> surveyResponses = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Set<Question> getQuestions() {
    return questions;
  }

  public List<SurveyResponse> getSurveyResponses() {
    return surveyResponses;
  }

  public void setSurveyResponses(List<SurveyResponse> surveyResponses) {
    this.surveyResponses = surveyResponses;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Survey survey = (Survey) o;

    if (!Objects.equals(id, survey.id)) return false;
    return Objects.equals(title, survey.title);
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (title != null ? title.hashCode() : 0);
    return result;
  }
}
