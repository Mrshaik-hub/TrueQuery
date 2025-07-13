package com.shaik.truequery.service;

import com.shaik.truequery.model.AnswerResponse;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

    public AnswerResponse getAnswer(String question) {
        // TODO: Replace this placeholder with real search + summarization logic
        String dummyAnswer = "This is a sample answer for your question: " + question;
        String dummySource = "https://truequery.ai/answers/sample";

        return new AnswerResponse(dummyAnswer, dummySource);
    }
}