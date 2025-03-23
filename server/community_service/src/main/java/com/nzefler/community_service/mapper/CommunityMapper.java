package com.nzefler.community_service.mapper;

import com.nzefler.community_service.dto.CommunityDTO;
import com.nzefler.community_service.model.Community;

public class CommunityMapper {

    public CommunityDTO toDTO(Community community){
        CommunityDTO response = new CommunityDTO();
        response.setCommunityId(community.getCommunityId());
        response.setDescription(community.getDescription());
        response.setCreatedOn(community.getCreatedOn());
        response.setName(community.getName());
        response.setOwner(community.getOwner());
        return response;
    }

}
