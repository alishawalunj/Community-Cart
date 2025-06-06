package com.nzefler.community_service.mapper;

import com.nzefler.community_service.dto.CommunityRequestDTO;
import com.nzefler.community_service.dto.CommunityResponseDTO;
import com.nzefler.community_service.model.Community;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommunityMapper {

    @Autowired
    private UserMapper userMapper;

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

}
