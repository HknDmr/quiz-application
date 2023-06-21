package org.kuhne.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.kuhne.model.Question;
import org.kuhne.model.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.*;

class DaoQuestionTest {

    private static DaoQuestion daoQuestion;

    @BeforeAll
    static void setup() {
        daoQuestion = new DaoQuestion();
    }

    @Test
    void testSaveQuestionAndSearchByTopic() {
        Response response1 = new Response("Response 1", true);
        Response response2 = new Response("Response 2", false);
        Response response3 = new Response("Response 3", true);
        List<Response> responseList = Arrays.asList(response1, response2, response3);

        Question question1 = new Question(1, 1, "Topic", 1, "Question 1 content", responseList);

        daoQuestion.saveQuestion(question1);

        String topicToSearch = "Topic";
        List<Question> questionsByTopic = daoQuestion.searchQuestionByTopic(topicToSearch);

        Assertions.assertEquals(1, questionsByTopic.size());

        Question searchedQuestion = questionsByTopic.get(0);
        Assertions.assertEquals(question1.getQuestionId(), searchedQuestion.getQuestionId());
        Assertions.assertEquals(question1.getQuizId(), searchedQuestion.getQuizId());
        Assertions.assertEquals(question1.getTopic(), searchedQuestion.getTopic());
        Assertions.assertEquals(question1.getDifficultyRank(), searchedQuestion.getDifficultyRank());
        Assertions.assertEquals(question1.getContent(), searchedQuestion.getContent());
        Assertions.assertEquals(question1.getResponses().size(), searchedQuestion.getResponses().size());

        for (int i = 0; i < question1.getResponses().size(); i++) {
            Response expectedResponse = question1.getResponses().get(i);
            Response searchedResponse = searchedQuestion.getResponses().get(i);

            Assertions.assertEquals(expectedResponse.getText(), searchedResponse.getText());
            Assertions.assertEquals(expectedResponse.isCorrect(), searchedResponse.isCorrect());
        }
    }

    @Test
    void testSaveResponses() {
        List<Response> responses = new ArrayList<>();
        responses.add(new Response("Response 1", true));
        responses.add(new Response("Response 2", false));
        responses.add(new Response("Response 3", true));

        daoQuestion.saveResponses(1, responses);

        List<Response> savedResponses = daoQuestion.getResponses(1);

        for (int i = 0; i < responses.size(); i++) {
            Response expectedResponse = responses.get(i);
            Response actualResponse = savedResponses.get(i);

            assertEquals(expectedResponse.getText(), actualResponse.getText());
            assertEquals(expectedResponse.isCorrect(), actualResponse.isCorrect());
        }
    }

    @Test
    void testUpdateQuestion() {
        Response response1 = new Response("Response 1", true);
        Response response2 = new Response("Response 2", false);
        Response response3 = new Response("Response 3", true);
        List<Response> responseList = Arrays.asList(response1, response2, response3);
        Question question = new Question(1,1,"Topic 1", 1, "Question 1 content", responseList);
        daoQuestion.updateQuestion(1, question);

        Question updatedQuestion = daoQuestion.getQuestion(1);

        assertEquals(question.getTopic(), updatedQuestion.getTopic());
        assertEquals(question.getDifficultyRank(), updatedQuestion.getDifficultyRank());
        assertEquals(question.getContent(), updatedQuestion.getContent());
    }

    @Test
    void testDeleteQuestion() {
        Response response1 = new Response("Response 1", true);
        Response response2 = new Response("Response 2", false);
        Response response3 = new Response("Response 3", true);
        List<Response> responseList = Arrays.asList(response1, response2, response3);
        Question question = new Question(1,1,"Topic 1", 1, "Question 1 content", responseList);
        daoQuestion.saveQuestion(question);

        daoQuestion.deleteQuestion(1);

        Question deletedQuestion = daoQuestion.getQuestion(1);

        assertNull(deletedQuestion);
    }


    @Test
    void testDeleteResponses() {
        Response response1 = new Response("Response 1", true);
        Response response2 = new Response("Response 2", false);
        Response response3 = new Response("Response 3", true);
        List<Response> responseList = Arrays.asList(response1, response2, response3);
        Question question = new Question(1,1,"Topic 1", 1, "Question 1 content", responseList);
        daoQuestion.saveQuestion(question);

        daoQuestion.deleteResponses(1);

        List<Response> deletedResponses = daoQuestion.getResponses(1);

        assertTrue(deletedResponses.isEmpty());
    }


}