package com.example.survey.exception;

import com.example.survey.exception.SurveyNotFoundException;
import com.example.survey.exception.SurveyResponseNotFoundException;
import com.example.survey.exception.SurveySubmissionIsAlreadyExistException;
import com.example.survey.exception.SurveySubmissionIsNotCompleteException;
import com.example.survey.response.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({SurveyNotFoundException.class, SurveyResponseNotFoundException.class})
  public ResponseEntity<ApiError> handleNotFoundException(RuntimeException e,
                                                          HttpServletRequest request) {
    return new ResponseEntity<>(new ApiError(
        e.getMessage(),
        request.getRequestURI(),
        HttpStatus.NOT_FOUND.value(),
        LocalDateTime.now()
    ), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(SurveySubmissionIsNotCompleteException.class)
  public ResponseEntity<ApiError> handleSurveySubmissionIsNotCompleteException(SurveySubmissionIsNotCompleteException e,
                                                                               HttpServletRequest request,
                                                                               HttpServletResponse response) {
    return new ResponseEntity<>(new ApiError(
        e.getMessage(),
        request.getRequestURI(),
        HttpStatus.BAD_REQUEST.value(),
        LocalDateTime.now()
    ), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(SurveySubmissionIsAlreadyExistException.class)
  public ResponseEntity<ApiError> handleSurveySubmissionIsAlreadyExistException(SurveySubmissionIsAlreadyExistException e,
                                                                                HttpServletRequest request) {
    return new ResponseEntity<>(new ApiError(
        e.getMessage(),
        request.getRequestURI(),
        HttpStatus.CONFLICT.value(),
        LocalDateTime.now()
    ), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                             WebRequest request) {
    var bindingErrors = ex.getBindingResult().getAllErrors()
        .stream()
        .map(err -> Map.of(((FieldError) err).getField(), Objects.requireNonNull(err.getDefaultMessage())))
        .map(Map::entrySet)
        .flatMap(Set::stream)
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    return new ResponseEntity<>(new ApiError(
        bindingErrors,
        request.getContextPath(),
        HttpStatus.BAD_REQUEST.value(),
        LocalDateTime.now()
    ), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleException(Exception e,
                                                  HttpServletRequest request) {
    return new ResponseEntity<>(new ApiError(
        e.getMessage(),
        request.getRequestURI(),
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        LocalDateTime.now()
    ), HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
