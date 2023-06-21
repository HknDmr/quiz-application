package org.kuhne.model;

import java.util.List;

public class Question {

    private int questionId;
    private int quizId;
    private String topic;
    private int difficultyRank;
    private String content;
    private List<Response> responses;


    public Question(int questionId, int quizId, String topic, int difficultyRank, String content, List<Response> responses) {
        this.questionId = questionId;
        this.quizId = quizId;
        this.topic = topic;
        this.difficultyRank = difficultyRank;
        this.content = content;
        this.responses = responses;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getDifficultyRank() {
        return difficultyRank;
    }

    public void setDifficultyRank(int difficultyRank) {
        this.difficultyRank = difficultyRank;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Response> getResponses() {
        return responses;
    }
    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }
}
