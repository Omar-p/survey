package com.example.survey.response;

import java.time.LocalDateTime;

public record ApiError(
    Object error,
    String path,
    int status,
    LocalDateTime timestamp
) {
}