package com.nzefler.community.client;

import com.nzefler.community.dto.UserRefDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Component
public class UserServiceClient {
    private final RestTemplate restTemplate;
    private final String userServiceUrl;

    public UserServiceClient(RestTemplate restTemplate,
                             @Value("${user-service.url}") String userServiceUrl) {
        this.restTemplate = restTemplate;
        this.userServiceUrl = userServiceUrl;
    }

    public UserRefDTO getUserByEmail(String email) {
        String url = userServiceUrl + "/auth/email/" + email;
        return restTemplate.getForObject(url, UserRefDTO.class);
    }
}
