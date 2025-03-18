package com.nzefler.community_service.mapper;

import com.nzefler.community_service.dto.UserResponseDTO;
import com.nzefler.community_service.model.User;

public class UserMapper {

    public UserResponseDTO toDTO(User user){
        UserResponseDTO userResponse = new UserResponseDTO();
        userResponse.setUserId(user.getUserId());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmailId(user.getEmailId());
        userResponse.setPassword(user.getPassword());
        return userResponse;
    }
}
