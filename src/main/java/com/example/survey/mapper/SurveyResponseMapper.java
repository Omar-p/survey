package com.example.survey.mapper;

import com.example.survey.entity.AnswerType;
import com.example.survey.entity.Choice;
import com.example.survey.entity.SurveyResponse;
import com.example.survey.entity.UserAnswer;
import com.example.survey.response.QuestionAnswerDTO;
import com.example.survey.response.SurveyResponseDTO;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SurveyResponseMapper {
  public SurveyResponseDTO mapToDTO(SurveyResponse surveyResponse) {
    var title = surveyResponse.getSurvey().getTitle();
    var userAnswers = surveyResponse.getUserAnswers();

    return new SurveyResponseDTO(title, userAnswers.stream()
        .map(this::mapToQuestionAnswerDTO)
        .collect(Collectors.toSet()));
  }

  private QuestionAnswerDTO mapToQuestionAnswerDTO(UserAnswer userAnswer) {
    String questionText = userAnswer.getQuestion().getText();

    if (userAnswer.isAnswered() && userAnswer.getQuestion().getType() != AnswerType.TEXT_ENTRY) {
      Set<String> choices = userAnswer.getSelectedChoices().stream()
          .map(this::getChoiceText)
          .collect(Collectors.toSet());
      return new QuestionAnswerDTO(questionText, null, choices, true);
    }

    return new QuestionAnswerDTO(questionText, userAnswer.getTextAnswer(), null, userAnswer.isAnswered());
  }

  private String getChoiceText(Choice choice) {
    return choice.getText();
  }
}
