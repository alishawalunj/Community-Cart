package com.nzefler.community.mapper;

import com.nzefler.community.dto.UserRequestDTO;
import com.nzefler.community.dto.UserResponseDTO;
import com.nzefler.community.model.User;
import org.springframework.stereotype.Component;

@Component
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

    public User toEntity(UserRequestDTO userRequestDTO){
        User toSaveUser = new User();
        toSaveUser.setEmailId(userRequestDTO.getEmailId());
        toSaveUser.setFirstName(userRequestDTO.getFirstName());
        toSaveUser.setLastName(userRequestDTO.getLastName());
        toSaveUser.setPassword(userRequestDTO.getPassword());
//        toSaveUser.setCommunity(userDTO.getCommunityId());
        return toSaveUser;
    }
}
