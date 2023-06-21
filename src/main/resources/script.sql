CREATE TABLE Quizz
(
    quiz_id SERIAL PRIMARY KEY
);

CREATE TABLE Question
(
    question_id     SERIAL PRIMARY KEY,
    quiz_id         INTEGER      NOT NULL,
    topic           VARCHAR(255) NOT NULL,
    difficulty_rank INTEGER      NOT NULL,
    content         TEXT         NOT NULL,
    FOREIGN KEY (quiz_id) REFERENCES Quizz (quiz_id)
);


CREATE TABLE Response
(
    response_id SERIAL PRIMARY KEY,
    question_id INTEGER NOT NULL,
    text        TEXT    NOT NULL,
    correct     BOOLEAN NOT NULL,
    FOREIGN KEY (question_id) REFERENCES Question (question_id)
);