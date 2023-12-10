package com.example.survey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestSurveyApplication {

  public static void main(String[] args) {
    SpringApplication.from(SurveyApplication::main).with(TestSurveyApplication.class).run(args);
  }

}
