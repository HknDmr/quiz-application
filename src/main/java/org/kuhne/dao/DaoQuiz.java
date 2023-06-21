package org.kuhne.dao;

import org.kuhne.model.Quiz;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.kuhne.dao.DatabaseConnection.getConnection;

public class DaoQuiz {

    public void saveQuiz(Quiz quiz) {
        try {
            PreparedStatement statement = getConnection().prepareStatement(
                    "INSERT INTO Quizz DEFAULT VALUES",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int quizId = generatedKeys.getInt(1);
                quiz.setQuizId(quizId);
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
