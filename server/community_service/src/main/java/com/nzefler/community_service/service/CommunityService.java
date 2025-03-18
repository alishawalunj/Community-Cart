package com.nzefler.community_service.service;

import com.nzefler.community_service.dto.CommunityResponseDTO;
import com.nzefler.community_service.model.Community;

import java.util.List;
import java.util.Optional;

public interface CommunityService {

    List<CommunityResponseDTO> findAllCommunities();
    CommunityResponseDTO findCommunityById(Long communityId);
    CommunityResponseDTO findCommunityByName(String name);
    String saveCommunity(Community community);
    Community updateCommunity(Community community);
    void deleteCommunity(Long communityId);
}
