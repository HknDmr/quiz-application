package org.kuhne.dao;

import org.kuhne.model.Question;
import org.kuhne.model.Response;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.kuhne.dao.DatabaseConnection.getConnection;

public class DaoQuestion {

    public DaoQuestion() {
    }

    public void saveQuestion(Question question) {
        try {
            PreparedStatement statement = getConnection().prepareStatement(
                    "INSERT INTO Question (question_id, quiz_id, topic, difficulty_rank, content) VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            statement.setInt(1, question.getQuestionId());
            statement.setInt(2, question.getQuizId());
            statement.setString(3, question.getTopic());
            statement.setInt(4, question.getDifficultyRank());
            statement.setString(5, question.getContent());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int questionId = generatedKeys.getInt(1);
                saveResponses(questionId, question.getResponses());
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void saveResponses(int questionId, List<Response> responses) {
        try {
            PreparedStatement statement = getConnection().prepareStatement(
                    "INSERT INTO Response (question_id, text, correct) VALUES (?, ?, ?)"
            );

            for (Response response : responses) {
                statement.setInt(1, questionId);
                statement.setString(2, response.getText());
                statement.setBoolean(3, response.isCorrect());
                statement.executeUpdate();
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateQuestion(int questionId, Question newQuestion) {
        try {
            PreparedStatement statement = getConnection().prepareStatement(
                    "UPDATE Question SET topic = ?, difficulty_rank = ?, content = ? WHERE question_id = ?"
            );
            statement.setString(1, newQuestion.getTopic());
            statement.setInt(2, newQuestion.getDifficultyRank());
            statement.setString(3, newQuestion.getContent());
            statement.setInt(4, questionId);
            statement.executeUpdate();

            deleteResponses(questionId);
            saveResponses(questionId, newQuestion.getResponses());

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteQuestion(int questionId) {
        try {
            deleteResponses(questionId);

            PreparedStatement statement = getConnection().prepareStatement(
                    "DELETE FROM Question WHERE question_id = ?"
            );
            statement.setInt(1, questionId);
            statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteResponses(int questionId) {
        try {
            PreparedStatement statement = getConnection().prepareStatement(
                    "DELETE FROM Response WHERE question_id = ?"
            );
            statement.setInt(1, questionId);
            statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Question> searchQuestionByTopic(String topic) {
        List<Question> questions = new ArrayList<>();

        try {
            PreparedStatement statement = getConnection().prepareStatement(
                    "SELECT * FROM Question WHERE topic = ?"
            );
            statement.setString(1, topic);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int questionId = resultSet.getInt("question_id");
                int quizId = resultSet.getInt("question_id");
                int difficultyRank = resultSet.getInt("difficulty_rank");
                String content = resultSet.getString("content");
                List<Response> responses = getResponses(questionId);

                Question question = new Question(questionId, quizId, topic, difficultyRank, content, responses);
                questions.add(question);
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questions;
    }


    List<Response> getResponses(int questionId) {
        List<Response> responses = new ArrayList<>();

        try {
            PreparedStatement statement = getConnection().prepareStatement(
                    "SELECT * FROM Response WHERE question_id = ?"
            );
            statement.setInt(1, questionId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String text = resultSet.getString("text");
                boolean correct = resultSet.getBoolean("correct");

                Response response = new Response(text, correct);
                responses.add(response);
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return responses;
    }

    public Question getQuestion(int questionId) {
        Question question = null;
        try {
            PreparedStatement statement = getConnection().prepareStatement(
                    "SELECT * FROM Question WHERE question_id = ?"
            );
            statement.setInt(1, questionId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int quizId = resultSet.getInt("quiz_id");
                String topic = resultSet.getString("topic");
                int difficultyRank = resultSet.getInt("difficulty_rank");
                String content = resultSet.getString("content");
                List<Response> responses = getResponses(questionId);

                question = new Question(questionId, quizId, topic, difficultyRank, content, responses);
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return question;
    }

}
