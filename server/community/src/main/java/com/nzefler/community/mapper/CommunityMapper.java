package com.nzefler.community.mapper;

import com.nzefler.community.dto.CommunityRequestDTO;
import com.nzefler.community.dto.CommunityResponseDTO;
import com.nzefler.community.dto.CommunityUserResponseDTO;
import com.nzefler.community.dto.UserResponseDTO;
import com.nzefler.community.model.Community;
import com.nzefler.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CommunityMapper {

    public CommunityResponseDTO toDTO(Community community){
        CommunityResponseDTO response = new CommunityResponseDTO();
        response.setCommunityId(community.getCommunityId());
        response.setDescription(community.getDescription());
        response.setCreatedOn(community.getCreatedOn());
        response.setName(community.getName());
        response.setOwner(community.getOwner());
        return response;
    }

    public Community toEntity(CommunityRequestDTO request){
        Community community = new Community();
//        response.setCommunityId(community.getCommunityId());
        community.setDescription(request.getDescription());
        community.setCreatedOn(request.getCreatedOn());
        community.setName(request.getName());
        community.setOwner(request.getOwner());
        return community;
    }

    public CommunityUserResponseDTO toEntity(Community community){
        CommunityUserResponseDTO response = new CommunityUserResponseDTO();
        response.setCommunityId(community.getCommunityId());
        response.setName(community.getName());
        response.setDescription(community.getDescription());
        response.setOwner(community.getOwner());
        response.setCreatedOn(community.getCreatedOn());
        Set<UserResponseDTO> users =new HashSet<>();
        for(User user: community.getUsers()){
            UserResponseDTO userResponseDTO = new UserResponseDTO();
            userResponseDTO.setUserId(user.getUserId());
            userResponseDTO.setFirstName(user.getFirstName());
            userResponseDTO.setLastName(user.getLastName());
            userResponseDTO.setEmailId(user.getEmailId());
            userResponseDTO.setPassword(user.getEmailId());
            users.add(userResponseDTO);
        }
        response.setUsers(users);
        return response;
    }

}
