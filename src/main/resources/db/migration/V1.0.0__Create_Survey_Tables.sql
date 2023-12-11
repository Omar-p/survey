CREATE SEQUENCE choice_id_seq INCREMENT BY 5;
CREATE SEQUENCE question_id_seq INCREMENT BY 5;
CREATE SEQUENCE survey_id_seq INCREMENT BY 5;
CREATE SEQUENCE survey_response_id_seq INCREMENT BY 5;
CREATE SEQUENCE user_answer_id_seq INCREMENT BY 5;

CREATE TABLE survey
(
    id    BIGINT PRIMARY KEY DEFAULT nextval('survey_id_seq'),
    title VARCHAR(255) NOT NULL,
    CONSTRAINT unique_survey_title UNIQUE (title)
);

CREATE TABLE survey_response
(
    id         BIGINT PRIMARY KEY DEFAULT nextval('survey_response_id_seq'),
    survey_id  BIGINT REFERENCES survey (id),
    user_email VARCHAR(255) NOT NULL,
    CONSTRAINT fk_survey_response_survey FOREIGN KEY (survey_id) REFERENCES survey (id)
);

CREATE TABLE question
(
    id        BIGINT PRIMARY KEY DEFAULT nextval('question_id_seq'),
    text      VARCHAR(255) NOT NULL,
    type      VARCHAR(50)  NOT NULL,
    required  BOOLEAN      NOT NULL,
    survey_id BIGINT REFERENCES survey (id),
    CONSTRAINT fk_question_survey FOREIGN KEY (survey_id) REFERENCES survey (id)
);

CREATE TABLE choice
(
    id          BIGINT PRIMARY KEY DEFAULT nextval('choice_id_seq'),
    text        VARCHAR(255) NOT NULL,
    question_id BIGINT REFERENCES question (id),
    CONSTRAINT fk_choice_question FOREIGN KEY (question_id) REFERENCES question (id),
    CONSTRAINT unique_choice_text UNIQUE (text)
);

CREATE TABLE user_answer
(
    id                 BIGINT PRIMARY KEY DEFAULT nextval('user_answer_id_seq'),
    survey_response_id BIGINT REFERENCES survey_response (id),
    question_id        BIGINT REFERENCES question (id),
    text_answer        VARCHAR(255),
    answered           BOOLEAN NOT NULL,
    CONSTRAINT fk_user_answer_survey_response FOREIGN KEY (survey_response_id) REFERENCES survey_response (id),
    CONSTRAINT fk_user_answer_question FOREIGN KEY (question_id) REFERENCES question (id)
);

CREATE TABLE user_answer_choice
(
    user_answer_id BIGINT REFERENCES user_answer (id),
    choice_id      BIGINT REFERENCES choice (id),
    PRIMARY KEY (user_answer_id, choice_id),
    CONSTRAINT fk_user_answer_choice_user_answer FOREIGN KEY (user_answer_id) REFERENCES user_answer (id),
    CONSTRAINT fk_user_answer_choice_choice FOREIGN KEY (choice_id) REFERENCES choice (id)
);

CREATE INDEX idx_survey_response_survey_id ON survey_response (survey_id);
CREATE INDEX idx_user_answer_survey_response_id ON user_answer (survey_response_id);
CREATE INDEX idx_user_answer_question_id ON user_answer (question_id);
CREATE INDEX idx_user_answer_choice_user_answer_id ON user_answer_choice (user_answer_id);
CREATE INDEX idx_user_answer_choice_choice_id ON user_answer_choice (choice_id);
