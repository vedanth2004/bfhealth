package com.example.bfhealth.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class WebhookService {

    private final RestTemplate restTemplate = new RestTemplate();

    public void executeTask() {
        // 1️⃣ Generate Webhook
        String webhookUrl = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", "John Doe");
        requestBody.put("regNo", "REG12347"); // Update your regNo
        requestBody.put("email", "john@example.com");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.exchange(webhookUrl, HttpMethod.POST, entity, Map.class);

        if(response.getStatusCode() == HttpStatus.OK) {
            Map<String, String> body = response.getBody();
            String webhook = body.get("webhook").toString();
            String accessToken = body.get("accessToken").toString();

            // 2️⃣ Solve SQL problem (logic depends on your regNo)
            String finalQuery = solveSQLProblem();

            // 3️⃣ Send SQL result to webhook
            HttpHeaders webhookHeaders = new HttpHeaders();
            webhookHeaders.setContentType(MediaType.APPLICATION_JSON);
            webhookHeaders.set("Authorization", accessToken);

            Map<String, String> finalBody = new HashMap<>();
            finalBody.put("finalQuery", finalQuery);

            HttpEntity<Map<String, String>> webhookEntity = new HttpEntity<>(finalBody, webhookHeaders);
            ResponseEntity<String> webhookResponse = restTemplate.postForEntity(webhook, webhookEntity, String.class);

            System.out.println("Webhook response: " + webhookResponse.getBody());
        }
    }

    private String solveSQLProblem() {
        // Your SQL logic here
        // Check which question based on last 2 digits of regNo
        // For now, returning placeholder
        return "SELECT * FROM users;";
    }
}
