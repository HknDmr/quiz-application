package org.kuhne.model;

import java.util.List;

public class Quiz {
    private int quizId;
    private List<Question> questions;

    public Quiz(List<Question> questions) {
        this.questions = questions;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
