# Survey App

## Overview

This repository contains the source code for the Survey App, a Spring Boot application that uses technologies such as Spring Data JPA, PostgreSQL, Flyway, TestContainer, Spring REST Docs, and Java.

## Technologies Used

- Spring Boot
- Spring Data JPA
- PostgreSQL
- Flyway
- TestContainer
- Spring REST Docs
- Java

## Getting Started

### Prerequisites

- Docker
- Docker Compose

```bash
git clone https://github.com/Omar-p/survey.git
cd survey
docker-compose up
```

## Retrieving Surveys and Storing Responses
- **Efficient Retrieval of Surveys and Storage of Responses:**
  The Survey App is designed to efficiently retrieve surveys and store responses, ensuring a streamlined user experience.

- **Optimized Queries to Eliminate the N+1 Problem:**
  Queries have been optimized to address the N+1 problem effectively. This optimization allows the application to fetch both survey questions and answers in a single call, enhancing performance and reducing database query overhead.

## Documentation
For detailed information on the API endpoints and usage, please refer to the [Survey App Documentation](https://surveyapidoc.s3.eu-central-1.amazonaws.com/index.html).

The documentation is automatically generated from tests to ensure accuracy and consistency.

Make sure to consult the documentation for a seamless integration and utilization of the application's features.

Explore the [Survey App Documentation](https://surveyapidoc.s3.eu-central-1.amazonaws.com/index.html) for a comprehensive reference.

