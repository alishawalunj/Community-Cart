package com.nzefler.community_service.mapper;

import com.nzefler.community_service.dto.CommunityResponseDTO;
import com.nzefler.community_service.dto.UserResponseDTO;
import com.nzefler.community_service.model.Community;

import java.util.List;

public class CommunityMapper {

    public CommunityResponseDTO toDTO(Community community){
        CommunityResponseDTO response = new CommunityResponseDTO();
        response.setCommunityId(community.getCommunityId());
        response.setDescription(community.getDescription());
        response.setCreatedOn(community.getCreatedOn());
        response.setName(community.getName());
        response.setOwner(community.getOwner());
//        if(!usersList.isEmpty()){
//            response.setMembersList(usersList);
//        }
        return response;
    }

}
