package com.nzefler.community_service.controller;

import com.nzefler.community_service.dto.CommunityDTO;
import com.nzefler.community_service.model.Community;
import com.nzefler.community_service.service.CommunityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
@Controller
public class CommunityController {

    @Autowired
    private CommunityServiceImpl communityService;

    @QueryMapping
    public List<CommunityDTO> getAllCommunities(){
        return communityService.findAllCommunities();
    }

    @QueryMapping
    public CommunityDTO getCommunityById(@Argument Long communityId){
        return communityService.findCommunityById(communityId);
    }

    @QueryMapping
    public CommunityDTO getCommunityByName(@Argument String name){
        return communityService.findCommunityByName(name);
    }

    @MutationMapping
    public String createCommunity(@Argument Community community){
        return communityService.saveCommunity(community);
    }

    @MutationMapping
    public Community updateCommunity(@Argument Community community){
        return communityService.updateCommunity(community);
    }

    @MutationMapping
    public void deleteCommunity(@Argument Long communityId){
        communityService.deleteCommunity(communityId);
    }
}

