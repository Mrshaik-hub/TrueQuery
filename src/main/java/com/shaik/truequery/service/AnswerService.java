package com.shaik.truequery.service;

import com.shaik.truequery.model.AnswerResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
public class AnswerService {
    @Value("${serpapi.key}")
    private String serpApiKey;

    public AnswerResponse getAnswer(String question) {
        // TODO: Replace this placeholder with real search + summarization logic
        String googleAnswer = fetchFromGoogle(question);
        return new AnswerResponse(googleAnswer, "https://google.com/search?q=" + URLEncoder.encode(question, StandardCharsets.UTF_8));
    }
    private String fetchFromGoogle(String query) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://serpapi.com/search.json?q=" + URLEncoder.encode(query, StandardCharsets.UTF_8) + "&api_key=" + serpApiKey;

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        Map<String, Object> result = response.getBody();

        List<Map<String, String>> organicResults = (List<Map<String, String>>) result.get("organic_results");

        if (organicResults != null && !organicResults.isEmpty()) {
            String snippet = organicResults.get(0).get("snippet");
            return snippet != null ? snippet : "No answer found.";
        }

        return "No relevant result found.";
    }
}