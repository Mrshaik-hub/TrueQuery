package com.shaik.truequery.service;
import com.shaik.truequery.model.AnswerResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AnswerServiceTest {
    private final AnswerService answerService = new AnswerService();

    @Test
    void shouldReturnAnswerResponse() {
        String question = "What is Spring Boot?";
        AnswerResponse response = answerService.getAnswer(question);

        assertNotNull(response);
        assertTrue(response.getAnswer().contains("Spring Boot"));
        assertTrue(response.getSource().startsWith("http"));
    }

    @Test
    void shouldReturnDefaultSourceIfNoRealLogic() {
        AnswerResponse response = answerService.getAnswer("Any question?");
        assertEquals("https://truequery.ai/answers/sample", response.getSource());
    }
}
