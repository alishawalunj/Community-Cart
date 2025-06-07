package com.nzefler.product_service.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class CommunityGraphQLClient {

    private final WebClient webClient;

    public CommunityGraphQLClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:8080/graphql")
                .build();
    }

    public List<Long> getUserCommunityIds(Long userId) {
        String query = """
            query {
              getUserCommunities(userId: %d) {
                communityId
              }
            }
        """.formatted(userId);

        Map<String, Object> requestBody = Map.of("query", query);

        try {
            Map<String, Object> response = webClient.post()
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (response == null || !response.containsKey("data")) {
                throw new RuntimeException("Invalid response from community-service");
            }

            Map<String, Object> data = (Map<String, Object>) response.get("data");
            List<Map<String, Object>> communities = (List<Map<String, Object>>) data.get("getUserCommunities");

            return communities.stream()
                    .map(comm -> Long.valueOf(comm.get("communityId").toString()))
                    .collect(Collectors.toList());

        } catch (WebClientResponseException e) {
            throw new RuntimeException("Failed to fetch user communities: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error while calling community-service: " + e.getMessage(), e);
        }
    }
}

