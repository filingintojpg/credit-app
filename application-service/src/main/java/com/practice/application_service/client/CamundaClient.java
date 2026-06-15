package com.practice.application_service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class CamundaClient {

    private final WebClient webClient;

    public CamundaClient(@Value("${camunda.url}") String camundaUrl,
                         @Value("${camunda.username}") String username,
                         @Value("${camunda.password}") String password) {
        this.webClient = WebClient.builder()
                .baseUrl(camundaUrl)
                .defaultHeaders(headers -> headers.setBasicAuth(username, password))
                .build();
    }

    public void startCreditApplicationProcess(Long applicationId) {
        Map<String, Object> body = Map.of(
                "variables", Map.of(
                        "applicationId", Map.of(
                                "value", applicationId,
                                "type", "Long"
                        )
                )
        );

        webClient.post()
                .uri("/process-definition/key/creditApplicationProcess/start")
                .bodyValue(body)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
