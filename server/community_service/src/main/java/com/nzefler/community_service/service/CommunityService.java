package com.nzefler.community_service.service;

import com.nzefler.community_service.dto.CommunityRequestDTO;
import com.nzefler.community_service.dto.CommunityResponseDTO;
import com.nzefler.community_service.dto.UserResponseDTO;
import com.nzefler.community_service.model.Community;

import java.util.List;

public interface CommunityService {

//    Basic CRUD
    List<CommunityResponseDTO> findAllCommunities();
    CommunityResponseDTO findCommunityById(Long communityId);
    CommunityResponseDTO saveCommunity(CommunityRequestDTO community);
    CommunityResponseDTO updateCommunity(CommunityRequestDTO community);
    void deleteCommunity(Long communityId);

//    Relationship methods
    Boolean addUsersToCommunity(Long communityId, Long userId);
    Boolean removeUsersFromCommunity(Long communityId, Long userId);
    List<UserResponseDTO> findAllCommunityUsers(Long communityId);

//    Utility methods
    CommunityResponseDTO findCommunityByName(String name);
}
