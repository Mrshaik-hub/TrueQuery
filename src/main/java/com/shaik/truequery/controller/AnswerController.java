package com.shaik.truequery.controller;

import com.shaik.truequery.model.QuestionRequest;
import com.shaik.truequery.model.AnswerResponse;
import com.shaik.truequery.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @PostMapping("/ask")
    public AnswerResponse askQuestion(@RequestBody QuestionRequest request) {
        return answerService.getAnswer(request.getQuestion());
    }
}