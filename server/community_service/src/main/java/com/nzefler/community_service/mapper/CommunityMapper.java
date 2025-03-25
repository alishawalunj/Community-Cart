package com.nzefler.community_service.mapper;

import com.nzefler.community_service.dto.CommunityDTO;
import com.nzefler.community_service.dto.CommunityDetailDTO;
import com.nzefler.community_service.dto.UserDTO;
import com.nzefler.community_service.model.Community;
import com.nzefler.community_service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CommunityMapper {

    @Autowired
    private UserMapper userMapper;

    public CommunityDTO toDTO(Community community){
        CommunityDTO response = new CommunityDTO();
        response.setCommunityId(community.getCommunityId());
        response.setDescription(community.getDescription());
        response.setCreatedOn(community.getCreatedOn());
        response.setName(community.getName());
        response.setOwner(community.getOwner());
        return response;
    }

    public CommunityDetailDTO toCommunityDetailDTO(Community community){
        CommunityDetailDTO response = new CommunityDetailDTO();
        response.setCommunityId(community.getCommunityId());
        response.setDescription(community.getDescription());
        response.setCreatedOn(community.getCreatedOn());
        response.setName(community.getName());
        response.setOwner(community.getOwner());
        if(community.getUsers() != null){
            Set<UserDTO> userDTOs = community.getUsers().stream()
                    .map(userMapper::toDTO)
                    .collect(Collectors.toSet());
            response.setUsers(userDTOs);
        }
        return response;
    }

}
