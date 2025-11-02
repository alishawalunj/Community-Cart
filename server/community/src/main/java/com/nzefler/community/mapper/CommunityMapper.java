package com.nzefler.community.mapper;

import com.nzefler.community.dto.*;
import com.nzefler.community.model.Community;
import com.nzefler.community.model.User;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CommunityMapper {

    private final UserMapper userMapper;

    public CommunityMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    public CommunityResponseDTO toResponseDTO(Community community) {
        CommunityResponseDTO dto = new CommunityResponseDTO();
        dto.setCommunityId(community.getCommunityId());
        dto.setName(community.getName());
        dto.setDescription(community.getDescription());
        dto.setCreatedOn(community.getCreatedOn());
        dto.setOwner(userMapper.toDTO(community.getOwner()));
        dto.setImage(community.getImage());
        return dto;
    }

    public Community toEntity(CommunityRequestDTO request, User owner) {
        Community community = new Community();
        community.setName(request.getName());
        community.setDescription(request.getDescription());
        community.setCreatedOn(request.getCreatedOn());
        community.setOwner(owner);
        community.setImage(request.getImage());
        return community;
    }

    public CommunityUserResponseDTO toUserResponseDTO(Community community) {
        CommunityUserResponseDTO dto = new CommunityUserResponseDTO();
        dto.setCommunityId(community.getCommunityId());
        dto.setName(community.getName());
        dto.setDescription(community.getDescription());
        dto.setCreatedOn(community.getCreatedOn());
        dto.setOwner(userMapper.toDTO(community.getOwner()));
        dto.setImage(community.getImage());

        Set<UserResponseDTO> users = new HashSet<>();
        for (User user : community.getUsers()) {
            users.add(userMapper.toDTO(user));
        }
        dto.setUsers(users);
        return dto;
    }
}
