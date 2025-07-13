# TrueQuery API – Full Documentation

## 📘 Overview

**TrueQuery** is a smart answer API built using Java and Spring Boot. It takes in a user's question, processes it, and returns a response that includes an answer and a source. This API can be further enhanced by integrating external sources like ChatGPT, Google Search, or custom knowledge bases.

---

## 📂 Project Structure

```
truequery-api/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/com/truequery/
│   │   │   ├── TrueQueryApiApplication.java
│   │   │   ├── controller/
│   │   │   │   └── AnswerController.java
│   │   │   ├── service/
│   │   │   │   └── AnswerService.java
│   │   │   └── model/
│   │   │       ├── QuestionRequest.java
│   │   │       └── AnswerResponse.java
│   │   └── resources/
│   │       ├── application.properties
│   └── test/
│       └── java/com/truequery/
│           └── TrueQueryApiApplicationTests.java
├── .gitignore
└── README.md
```

---

## 🔧 Components Explained

### 1. **QuestionRequest.java**

```java
public class QuestionRequest {
    private String question;

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
}
```

* Represents incoming request from client
* JSON payload should contain a `question` field

### 2. **AnswerResponse.java**

```java
public class AnswerResponse {
    private String answer;
    private String source;

    public AnswerResponse(String answer, String source) {
        this.answer = answer;
        this.source = source;
    }

    public String getAnswer() { return answer; }
    public String getSource() { return source; }
}
```

* Represents the outgoing response
* Contains `answer` and `source` fields

### 3. **AnswerService.java**

```java
@Service
public class AnswerService {
    public AnswerResponse getAnswer(String question) {
        String sampleAnswer = "This is a sample answer for your question: " + question;
        String source = "https://truequery.ai/answers/sample";
        return new AnswerResponse(sampleAnswer, source);
    }
}
```

* Handles business logic
* Returns mock response now, but can call real APIs (like ChatGPT) later

### 4. **AnswerController.java**

```java
@RestController
@RequestMapping("/api")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @PostMapping("/ask")
    public ResponseEntity<AnswerResponse> askQuestion(@RequestBody QuestionRequest request) {
        AnswerResponse response = answerService.getAnswer(request.getQuestion());
        return ResponseEntity.ok(response);
    }
}
```

* Exposes REST endpoint `/api/ask`
* Accepts POST requests with JSON body containing a `question`
* Returns JSON response with `answer` and `source`

### 5. **TrueQueryApiApplication.java**

```java
@SpringBootApplication
public class TrueQueryApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(TrueQueryApiApplication.class, args);
    }
}
```

* Main entry point for the Spring Boot application

---

## 🧪 How to Run

### 1. Build & Start

```bash
mvn clean install
mvn spring-boot:run
```

### 2. Test in Postman

* **Method:** POST
* **URL:** [http://localhost:8080/api/ask](http://localhost:8080/api/ask)
* **Header:** Content-Type: application/json
* **Body:**

```json
{
  "question": "What is Java?"
}
```

* **Response:**

```json
{
  "answer": "This is a sample answer for your question: What is Java?",
  "source": "https://truequery.ai/answers/sample"
}
```

---

## 🤖 How to Connect ChatGPT API (OpenAI)

### 1. Add OpenAI Dependency (Optional)

Use `RestTemplate` or `WebClient` in Spring to call ChatGPT.

### 2. Add your API key

In `application.properties`:

```properties
openai.api.key=sk-xxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

### 3. Modify AnswerService.java

```java
@Service
public class AnswerService {

    @Value("${openai.api.key}")
    private String apiKey;

    public AnswerResponse getAnswer(String question) {
        String responseFromGPT = callOpenAI(question);
        return new AnswerResponse(responseFromGPT, "https://openai.com");
    }

    private String callOpenAI(String question) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-3.5-turbo");
        body.put("messages", List.of(Map.of("role", "user", "content", question)));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "https://api.openai.com/v1/chat/completions",
                entity,
                Map.class
        );

        List<Map> choices = (List<Map>) response.getBody().get("choices");
        Map choice = (Map) choices.get(0);
        Map message = (Map) choice.get("message");
        return message.get("content").toString();
    }
}
```

### 🔐 Notes:

* Don't hardcode API keys — use `.env` or secure vaults for production
* You must add dependency for `spring-boot-starter-web` and `spring-boot-configuration-processor`

---

## 🧪 Tests

* `TrueQueryApplicationTests.java` verifies app context loads
* Add `AnswerControllerTest.java` to mock endpoint requests

---

## ✅ Summary

| Component          | Purpose                         |
| ------------------ | ------------------------------- |
| Controller         | Handles HTTP requests           |
| Service            | Business logic, fetches answers |
| Model              | Defines request/response format |
| OpenAI Integration | Dynamic answers from GPT        |
| Postman / Swagger  | Test and explore endpoints      |

This API is ready to evolve into a smart search backend. You can plug in ChatGPT, Google, PDF docs, or databases to create a powerful query engine.

