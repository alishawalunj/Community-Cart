package com.nzefler.order.client;

import com.nzefler.order.dto.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class UserServiceClient {

    private final RestTemplate restTemplate;
    private final String userServiceUrl;

    public UserServiceClient(RestTemplate restTemplate, @Value("${user-service.url}") String userServiceUrl) {
        this.restTemplate = restTemplate;
        this.userServiceUrl = userServiceUrl;
    }

    public String getUserEmail(Long userId){
        UserDTO dto = restTemplate.getForObject(userServiceUrl + "/auth/id" + userId, UserDTO.class);
        return dto != null ? dto.getEmail() : null;
    }
}
