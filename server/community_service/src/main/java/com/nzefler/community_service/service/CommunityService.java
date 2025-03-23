package com.nzefler.community_service.service;

import com.nzefler.community_service.dto.CommunityDTO;
import com.nzefler.community_service.model.Community;

import java.util.List;

public interface CommunityService {

    List<CommunityDTO> findAllCommunities();
    CommunityDTO findCommunityById(Long communityId);
    CommunityDTO findCommunityByName(String name);
    String saveCommunity(Community community);
    Community updateCommunity(Community community);
    void deleteCommunity(Long communityId);
}
