package com.example.survey.service;

import com.example.survey.entity.AnswerType;
import com.example.survey.entity.SurveyResponse;
import com.example.survey.entity.UserAnswer;
import com.example.survey.exception.SurveyNotFoundException;
import com.example.survey.exception.SurveySubmissionIsAlreadyExistException;
import com.example.survey.exception.SurveySubmissionIsNotCompleteException;
import com.example.survey.repository.ChoiceRepository;
import com.example.survey.repository.QuestionRepository;
import com.example.survey.repository.SurveyRepository;
import com.example.survey.repository.SurveyResponseRepository;
import com.example.survey.request.AnswerSubmission;
import com.example.survey.request.SurveySubmissionRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class SubmitSurveyService {

  private final SurveyRepository surveyRepository;
  private final SurveyResponseRepository surveyResponseRepository;
  private final QuestionRepository questionRepository;
  private final ChoiceRepository choiceRepository;


  public SubmitSurveyService(SurveyRepository surveyRepository, SurveyResponseRepository surveyResponseRepository, QuestionRepository questionRepository, ChoiceRepository choiceRepository) {
    this.surveyRepository = surveyRepository;
    this.surveyResponseRepository = surveyResponseRepository;
    this.questionRepository = questionRepository;
    this.choiceRepository = choiceRepository;
  }

  public UUID submitSurvey(SurveySubmissionRequest surveySubmissionRequest) {
    Long id = validateSurveySubmission(surveySubmissionRequest);
    SurveyResponse surveyResponse = new SurveyResponse(
        surveyRepository.getReferenceById(id),
        surveySubmissionRequest.submissionId(),
        surveySubmissionRequest.email(),
        getUserAnswers(surveySubmissionRequest.answers())
    );
    surveyResponseRepository.save(surveyResponse);

    return surveyResponse.getSubmissionId();
  }

  private Long validateSurveySubmission(SurveySubmissionRequest surveySubmissionRequest) {
    Long surveyId = surveyRepository.findIdByTitle(surveySubmissionRequest.surveyTitle())
        .orElseThrow(() -> new SurveyNotFoundException("Survey with title %s not found".formatted(surveySubmissionRequest.surveyTitle())));

    boolean isSubmissionIdExists = surveyResponseRepository.existsBySubmissionId(surveySubmissionRequest.submissionId());
    if (isSubmissionIdExists) {
      throw new SurveySubmissionIsAlreadyExistException("Survey submission with id %s is already exist".formatted(surveySubmissionRequest.submissionId()));
    }

    boolean isComplete = surveySubmissionRequest.answers().stream()
        .filter(AnswerSubmission::required)
        .allMatch(AnswerSubmission::answered);
    if (!isComplete) {
      throw new SurveySubmissionIsNotCompleteException("Some required questions are not answered");
    }

    return surveyId;
  }

  private List<UserAnswer> getUserAnswers(List<AnswerSubmission> answers) {
    List<UserAnswer> userAnswers = new ArrayList<>();
    userAnswers.addAll(processUnansweredQuestion(answers));
    userAnswers.addAll(processAnsweredTextEntryQuestion(answers));
    userAnswers.addAll(processAnsweredMultipleChoiceQuestion(answers));
    return userAnswers;
  }

  private List<UserAnswer> processUnansweredQuestion(List<AnswerSubmission> answers) {
    return answers.stream()
        .filter(a -> !a.answered())
        .map(a -> UserAnswer.builder()
            .question(questionRepository.getReferenceById(a.questionId()))
            .answered(false)
            .build()
        ).collect(Collectors.toList());
  }

  private List<UserAnswer> processAnsweredTextEntryQuestion(List<AnswerSubmission> answers) {
    return answers.stream()
        .filter(AnswerSubmission::answered)
        .filter(a -> a.answerType().equals(AnswerType.TEXT_ENTRY))
        .map(a -> UserAnswer.builder()
            .question(questionRepository.getReferenceById(a.questionId()))
            .answered(true)
            .textAnswer(a.text())
            .build()
        ).collect(Collectors.toList());
  }

  private List<UserAnswer> processAnsweredMultipleChoiceQuestion(List<AnswerSubmission> answers) {
    return answers.stream()
        .filter(AnswerSubmission::answered)
        .filter(a -> !a.answerType().equals(AnswerType.TEXT_ENTRY))
        .map(a -> UserAnswer.builder()
            .question(questionRepository.getReferenceById(a.questionId()))
            .answered(true)
            .selectedChoices(a.choiceIds().stream()
                .map(choiceRepository::getReferenceById)
                .collect(Collectors.toSet())
            ).build()
        ).collect(Collectors.toList());
  }
}
