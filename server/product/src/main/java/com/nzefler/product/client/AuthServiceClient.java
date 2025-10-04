package com.nzefler.product.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class AuthServiceClient {

    private final RestTemplate restTemplate;
    private final String communityServiceClient = "http://localhost:8081/community-service/validate";

    public AuthServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean isTokenValid(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        System.out.println("Printing headersss"+ headers);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        try{
            ResponseEntity<String> response = restTemplate.exchange(communityServiceClient, HttpMethod.GET, entity, String.class);
            return response.getStatusCode().is2xxSuccessful();
        }catch(Exception e){
            return false;
        }
    }

    public List<Long> getUserCommunityIds(Long userId, String token){
        String url = "http://localhost:8081/community-service/users/" + userId + "/communityList";;
        HttpHeaders headers = new HttpHeaders();
        if (!token.startsWith("Bearer ")) {
            token = "Bearer " + token;
        }

        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        try{
            ResponseEntity<Long[]> response = restTemplate.exchange(url,
                    HttpMethod.GET,
                    entity,
                    Long[].class);
            return response.getBody() != null ? Arrays.asList(response.getBody()) : new ArrayList<>();
        } catch (Exception e) {
            throw new RuntimeException("failed to fetch user's communities");
        }
    }
}

