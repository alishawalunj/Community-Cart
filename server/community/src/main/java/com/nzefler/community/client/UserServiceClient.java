package com.nzefler.community.client;

import com.nzefler.community.dto.UserResponseDTO;
import com.nzefler.community.model.UserRef;
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

    public UserRef getUserByEmail(String email) {
        UserResponseDTO dto = restTemplate.getForObject(
                userServiceUrl + "/auth/email/" + email, UserResponseDTO.class);
        return toUserRef(dto);
    }

    public UserRef getUserById(Long userId) {
        UserResponseDTO dto = restTemplate.getForObject(
                userServiceUrl + "/auth/id/" + userId, UserResponseDTO.class);
        return toUserRef(dto);
    }

    private UserRef toUserRef(UserResponseDTO dto) {
        UserRef ref = new UserRef();
        ref.setUserId(dto.getUserId());
        ref.setDisplayName(dto.getFirstName() + " " + dto.getLastName());
        ref.setImage(dto.getImageUrl());
        return ref;
    }
}