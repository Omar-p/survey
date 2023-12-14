INSERT INTO survey (id, title)
VALUES (1, 'Sample Survey');

INSERT INTO question (id, text, type, required, survey_id)
VALUES (1, 'What is your favorite color?', 'SINGLE_CHOICE', true, 1),
       (2, 'Select your preferred programming languages', 'MULTIPLE_CHOICES', false, 1),
       (3, 'Provide feedback on our service', 'TEXT_ENTRY', true, 1);

INSERT INTO choice (id, text, question_id)
VALUES (1, 'Red', 1),
       (2, 'Blue', 1),
       (3, 'Green', 1),
       (4, 'Java', 2),
       (5, 'Python', 2),
       (6, 'JavaScript', 2);
select nextval('choice_id_seq');
select nextval('choice_id_seq');
select nextval('survey_id_seq');
select nextval('question_id_seq');
