package com.example.survey.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(
    name = "choice",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "text")
    }
)
public class Choice {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "choice_id_seq")
  @SequenceGenerator(
      name = "choice_id_seq",
      allocationSize = 5
  )
  private Long id;

  @Column(nullable = false)
  private String text;

  @ManyToOne
  @JoinColumn(name = "question_id")
  private Question question;

  public Choice() {
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

  public Question getQuestion() {
    return question;
  }

  public void setQuestion(Question question) {
    this.question = question;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Choice choice = (Choice) o;

    if (!Objects.equals(id, choice.id)) return false;
    return Objects.equals(text, choice.text);
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (text != null ? text.hashCode() : 0);
    return result;
  }
}