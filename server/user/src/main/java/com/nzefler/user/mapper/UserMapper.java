package com.nzefler.user.mapper;

import com.nzefler.user.dto.UserRequestDTO;
import com.nzefler.user.dto.UserResponseDTO;
import com.nzefler.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequestDTO request) {
        if (request == null) {
            return null;
        }
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setImageUrl(request.getImageUrl());
        user.setRole(request.getRole());
        return user;
    }

    public UserResponseDTO toResponseDTO(User user) {
        if (user == null) {
            return null;
        }

        UserResponseDTO response = new UserResponseDTO();
        response.setUserId(user.getUserId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setImageUrl(user.getImageUrl());
        response.setRole(user.getRole());
        return response;
    }
}
