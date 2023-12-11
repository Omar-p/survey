package com.example.survey;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SurveyControllerTest {
  String surveySubmission = """
      {
        "email": "user@example.com",
        "submissionId": "a4e7b6c2-5b52-4a8f-a8dd-39430fd5a7dc",
        "questions": [
          {
            "questionId": 1,
            "choiceIds": [1],
            "text": null,
            "questionType": null,
            "answered": true,
            "required": true
          },
          {
            "questionId": 2,
            "choiceIds": [5, 6],
            "text": null,
            "questionType": null,
            "answered": true,
            "required": false
          },
          {
            "questionId": 3,
            "choiceIds": null,
            "text": "Great service!",
            "questionType": null,
            "answered": true,
            "required": true
          }
        ]
      }
      """;

  @Autowired
  private MockMvc mockMvc;


  @Test
  void itShouldReturnSurveySubmissionIdWhenSurveySubmittedCorrectly() throws Exception {
    mockMvc.perform(post("/api/surveys")
            .param("surveyTitle", "Sample Survey")
        .contentType(MediaType.APPLICATION_JSON)
        .content(surveySubmission))
        .andExpect(header().string("Location", containsString("a4e7b6c2-5b52-4a8f-a8dd-39430fd5a7dc")))
        .andExpect(status().isCreated())
        .andDo(document("submit-survey"));
  }


}