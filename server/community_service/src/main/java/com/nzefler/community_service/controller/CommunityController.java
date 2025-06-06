package com.nzefler.community_service.controller;

import com.nzefler.community_service.dto.CommunityRequestDTO;
import com.nzefler.community_service.dto.CommunityResponseDTO;
import com.nzefler.community_service.dto.UserResponseDTO;
import com.nzefler.community_service.model.Community;
import com.nzefler.community_service.service.CommunityServiceImpl;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
@Controller
public class CommunityController {

    private final CommunityServiceImpl communityService;

    public CommunityController(CommunityServiceImpl communityService) {
        this.communityService = communityService;
    }

    @QueryMapping
    public List<CommunityResponseDTO> getAllCommunities(){
        return communityService.findAllCommunities();
    }

    @QueryMapping
    public CommunityResponseDTO getCommunityById(@Argument Long communityId){
        return communityService.findCommunityById(communityId);
    }

    @QueryMapping
    public CommunityResponseDTO getCommunityByName(@Argument String name){
        return communityService.findCommunityByName(name);
    }

    @MutationMapping
    public CommunityResponseDTO createCommunity(@Argument("community") CommunityRequestDTO dto){
        return communityService.saveCommunity(dto);
    }

    @MutationMapping
    public CommunityResponseDTO updateCommunity(@Argument("community") CommunityRequestDTO dto){
        return communityService.updateCommunity(dto);
    }

    @MutationMapping
    public void deleteCommunity(@Argument Long communityId){
        communityService.deleteCommunity(communityId);
    }

    @MutationMapping
    public Boolean addUsersToCommunity(@Argument Long communityId, @Argument Long userId){
         return communityService.addUsersToCommunity(communityId,userId);
    }

    @MutationMapping
    public Boolean removeUsersFromCommunity(@Argument Long communityId, @Argument Long userId){
         return communityService.removeUsersFromCommunity(communityId,userId);
    }

    @QueryMapping
    public List<UserResponseDTO> getAllCommunityUsers(@Argument Long communityId){
        return communityService.findAllCommunityUsers(communityId);
    }
}

