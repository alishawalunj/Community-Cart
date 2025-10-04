package com.nzefler.order.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthServiceClient {
    private final RestTemplate restTemplate;
    private final String communityServiceClient = "http://localhost:8081/community-service/validate";

    public AuthServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean isTokenValid(String token){
        HttpHeaders headers = new HttpHeaders();
        if(!token.startsWith("Bearer ")){
            token = "Bearer " + token;
        }
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        try{
            ResponseEntity<String> response = restTemplate.exchange(
                    communityServiceClient,
                    HttpMethod.GET,
                    entity,
                    String.class
            );
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }
}
