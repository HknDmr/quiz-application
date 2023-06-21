package org.kuhne;

import org.kuhne.dao.DaoQuestion;
import org.kuhne.dao.DaoQuiz;
import org.kuhne.model.Question;
import org.kuhne.model.Quiz;
import org.kuhne.model.Response;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.kuhne.dao.DatabaseConnection.closeDatabaseConnection;
import static org.kuhne.dao.DatabaseConnection.getConnection;


public class App {
    public static void main(String[] args) throws SQLException {
        DaoQuestion daoQuestion = new DaoQuestion();
        DaoQuiz daoQuiz = new DaoQuiz();

        try {

            Response response1 = new Response("Response 1", true);
            Response response2 = new Response("Response 2", false);
            Response response3 = new Response("Response 3", true);
            List<Response> responseList = Arrays.asList(response1, response2, response3);


            Response response4 = new Response("Response 4", true);
            Response response5 = new Response("Response 5", false);
            Response response6 = new Response("Response 6", false);
            List<Response> responseList2 = Arrays.asList(response4, response5, response6);

            Question question1 = new Question(1,1,"Topic 1", 1, "Question 1 content", responseList);
            Question question2 = new Question(2, 1,"Topic 2", 2, "Question 2 content", responseList2);

            Quiz quiz = new Quiz(Arrays.asList(question1, question2));
            daoQuiz.saveQuiz(quiz);

            daoQuestion.saveQuestion(question1);
            daoQuestion.saveQuestion(question2);

            int questionIdToUpdate = 1;
            Response updatedResponse1 = new Response("Response 1", true);
            Response updatedResponse2 = new Response("Response 2", false);
            List<Response> updatedResponseList = Arrays.asList(updatedResponse1, updatedResponse2);
            Question updatedQuestion = new Question(questionIdToUpdate, 1, "Updated Topic",2, "Updated Question content", updatedResponseList);
            daoQuestion.updateQuestion(questionIdToUpdate, updatedQuestion);

            daoQuestion.deleteQuestion(questionIdToUpdate);

            String topicToSearch = "Topic 1";
            List<Question> questionsByTopic = daoQuestion.searchQuestionByTopic(topicToSearch);
            System.out.println("Questions by Topic '" + topicToSearch + "':");
            for (Question question : questionsByTopic) {
                System.out.println("Question ID: " + question.getQuestionId());
                System.out.println("Topic: " + question.getTopic());
                System.out.println("Difficulty Rank: " + question.getDifficultyRank());
                System.out.println("Content: " + question.getContent());

                List<Response> responses = question.getResponses();
                for (Response response : responses) {
                    System.out.println("Response: " + response.getText() + ", Correct: " + response.isCorrect());
                }

                System.out.println("---------------------------");
            }
        } finally {
            closeDatabaseConnection(getConnection());
        }


    }
}
