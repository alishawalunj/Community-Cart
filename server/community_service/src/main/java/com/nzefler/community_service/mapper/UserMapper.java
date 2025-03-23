package com.nzefler.community_service.mapper;

import com.nzefler.community_service.dto.UserDTO;
import com.nzefler.community_service.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDTO(User user){
        UserDTO userResponse = new UserDTO();
        userResponse.setUserId(user.getUserId());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmailId(user.getEmailId());
        userResponse.setPassword(user.getPassword());
        return userResponse;
    }

    public User toEntity(UserDTO userDTO){
        User toSaveUser = new User();
        toSaveUser.setEmailId(userDTO.getEmailId());
        toSaveUser.setFirstName(userDTO.getFirstName());
        toSaveUser.setLastName(userDTO.getLastName());
        toSaveUser.setPassword(userDTO.getPassword());
//        toSaveUser.setCommunity(userDTO.getCommunityId());
        return toSaveUser;
    }
}
