package com.example.survey.controller;

import com.example.survey.AbstractPGTestcontainer;
import com.example.survey.repository.SurveyResponseRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SurveyControllerTest extends AbstractPGTestcontainer {


  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private SurveyResponseRepository surveyResponseRepository;

  @AfterEach
  void tearDown() {
    surveyResponseRepository.deleteAll();
  }


  @Test
  void itShouldReturnSurveySubmissionIdWhenSurveySubmittedCorrectly() throws Exception {
    // Load the JSON file as a Resource
    String jsonRequest = getJsonRequest("requests/survey-submissions-with-all-questions-answered.json");

    mockMvc.perform(post("/api/surveys")
          .contentType(MediaType.APPLICATION_JSON)
          .content(jsonRequest))
        .andExpect(header().exists("Location"))
        .andExpect(status().isCreated())
        .andDo(document("survey-submissions-with-all-questions-answered"));
  }


  @Test
  void itShouldReturnSurveySubmissionIdWhenSurveySubmittedCorrectlyWithUnansweredOptionalQuestion() throws Exception {
    String jsonRequest = getJsonRequest("requests/survey-submissions-with-unanswered-optional-question.json");

    mockMvc.perform(post("/api/surveys")
          .contentType(MediaType.APPLICATION_JSON)
          .content(jsonRequest))
        .andExpect(header().exists("Location"))
        .andExpect(status().isCreated())
        .andDo(document("survey-submissions-with-unanswered-optional-question"));
  }
  @Test
  void itShouldReturn400WhenSurveySubmittedWithUnansweredRequiredQuestion() throws Exception {
    String jsonRequest = getJsonRequest("requests/survey-submissions-with-unanswered-required-questions.json");

    mockMvc.perform(post("/api/surveys")
          .contentType(MediaType.APPLICATION_JSON)
          .content(jsonRequest))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error", containsString("Some required questions are not answered")))
        .andDo(document("survey-submissions-with-missing-required-question",
            preprocessResponse(Preprocessors.prettyPrint())));
  }


  @NotNull
  private static String getJsonRequest(String path) throws IOException {
    // Load the JSON file as a Resource
    Resource resource = new ClassPathResource(path);

    // Convert Resource content to String
    return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
  }

}