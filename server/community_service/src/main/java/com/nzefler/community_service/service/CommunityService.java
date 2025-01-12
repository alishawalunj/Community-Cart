package com.nzefler.community_service.service;

import com.nzefler.community_service.model.Community;

import java.util.List;
import java.util.Optional;

public interface CommunityService {

    List<Community> getAllCommunities();
    Optional<Community> getCommunityById(Long communityId);
    Community createCommunity(Community community);
    Community updateCommunity(Community community);
    void deleteCommunity(Long communityId);
}
