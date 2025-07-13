package com.shaik.truequery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shaik.truequery.model.AnswerResponse;
import com.shaik.truequery.model.QuestionRequest;
import com.shaik.truequery.service.AnswerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class AnswerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnswerService answerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void askEndpointReturnsAnswer() throws Exception {
        String question = "What is Java?";
        QuestionRequest request = new QuestionRequest();
        request.setQuestion(question);

        AnswerResponse mockResponse = new AnswerResponse(
                "Java is a programming language.",
                "https://example.com/java"
        );

        when(answerService.getAnswer(question)).thenReturn(mockResponse);

        mockMvc.perform(post("/api/ask")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.answer").value("Java is a programming language."))
                .andExpect(jsonPath("$.source").value("https://example.com/java"));
    }

    @Test
    void askEndpointWithEmptyQuestionReturns200() throws Exception {
        QuestionRequest request = new QuestionRequest();
        request.setQuestion("");

        AnswerResponse mockResponse = new AnswerResponse(
                "This is a sample answer for your question: ",
                "https://truequery.ai/answers/sample"
        );

        when(answerService.getAnswer("")).thenReturn(mockResponse);

        mockMvc.perform(post("/api/ask")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.answer").value("This is a sample answer for your question: "))
                .andExpect(jsonPath("$.source").value("https://truequery.ai/answers/sample"));
    }
}
