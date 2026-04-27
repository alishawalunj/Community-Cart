package com.nzefler.product.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Component
public class CommunityServiceClient {

    private final RestTemplate restTemplate;
    private final String communityServiceUrl;

    public CommunityServiceClient(RestTemplate restTemplate, @Value("${community-service.url}") String communityServiceUrl) {
        this.restTemplate = restTemplate;
        this.communityServiceUrl = communityServiceUrl;
    }

    public List<Long> getUserCommunityIds(Long userId){
        String url = communityServiceUrl + "/api/users/" + userId + "/communities";
        ResponseEntity<List<Long>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Long>>() {
        });
        return response.getBody();
    }
}
