package com.example.survey.mapper;

import com.example.survey.entity.Question;
import com.example.survey.entity.Survey;
import com.example.survey.response.ChoiceDTO;
import com.example.survey.response.QuestionDTO;
import com.example.survey.response.SurveyDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SurveyToSurveyDTOMapper {

  public SurveyDTO toSurveyResponseDTO(Survey survey) {
    return new SurveyDTO(survey.getTitle(), getQuestions(survey));
  }

  private List<QuestionDTO> getQuestions(Survey survey) {
    return survey.getQuestions().stream().map(question -> new QuestionDTO(question.getId(), question.getText(), question.getType(), getChoices(question),
        question.isRequired())).collect(Collectors.toList());
  }

  private List<ChoiceDTO> getChoices(Question question) {
    return question.getChoices().stream().map(choice -> new ChoiceDTO(choice.getId(), choice.getText()))
        .collect(Collectors.toList());
  }
}
