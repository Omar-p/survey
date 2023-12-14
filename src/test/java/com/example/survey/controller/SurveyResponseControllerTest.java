package com.example.survey.controller;

import com.example.survey.AbstractPGTestcontainer;
import com.example.survey.repository.SurveyResponseRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SurveyResponseControllerTest extends AbstractPGTestcontainer {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private SurveyResponseRepository surveyResponseRepository;

  @AfterEach
  void tearDown() {
    surveyResponseRepository.deleteAll();
  }


  @BeforeEach
  void setUp() throws Exception {
    // Load the JSON file as a Resource
    String jsonRequest = getJsonRequest("requests/survey-submissions-with-unanswered-optional-question.json");

    mockMvc.perform(post("/api/surveys")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonRequest));

    String jsonRequest2 = getJsonRequest("requests/survey-submissions-with-all-questions-answered.json");

    mockMvc.perform(post("/api/surveys")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonRequest2));

  }

  @Test
  void itShouldReturnSurveyResponseWhenSurveyResponseExist() throws Exception {

    mockMvc.perform(get("/api/surveys-responses")
          .param("submissionId", "33ec96d5-bc49-418b-8ffb-288e3a037a65"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.surveyTitle").value("Sample Survey"))
        .andExpect(jsonPath("$.userAnswers.size()").value(3))
        .andDo(document("survey-responses-by-submission-id", preprocessResponse(Preprocessors.prettyPrint())));
  }

  @Test
  void itShouldReturnAllSurveyResponseByTitleWhenSurveyResponseExist() throws Exception {

    mockMvc.perform(get("/api/surveys-responses/title/{surveyTitle}", "Sample Survey"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(2))
        .andDo(document("survey-responses-by-title", preprocessResponse(Preprocessors.prettyPrint())));
  }

  @Test
  void itShouldReturn404WhenSurveyResponseDoesNotExist() throws Exception {

    String submissionId = "ae506fb0-7bf4-479c-82f3-1043cde0e213";
    mockMvc.perform(get("/api/surveys-responses")
          .param("submissionId", submissionId))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.error").value("No survey response found with submission ID: %s.".formatted(submissionId)))
        .andDo(document("survey-responses-by-submission-id-not-found", preprocessResponse(Preprocessors.prettyPrint())));
  }


  @Test
  void itShouldReturn404WhenSurveyResponseDoesNotExistByTitle() throws Exception {

    String surveyTitle = "Sample Survey 2";
    mockMvc.perform(get("/api/surveys-responses/title/{surveyTitle}", surveyTitle))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.error").value("No survey found with title: %s.".formatted(surveyTitle)))
        .andDo(document("survey-responses-by-title-not-found", preprocessResponse(Preprocessors.prettyPrint())));
  }

  @NotNull
  private static String getJsonRequest(String path) throws IOException {
    // Load the JSON file as a Resource
    Resource resource = new ClassPathResource(path);

    // Convert Resource content to String
    return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
  }
}