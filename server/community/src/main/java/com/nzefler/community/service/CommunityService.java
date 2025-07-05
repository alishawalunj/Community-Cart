package com.nzefler.community.service;

import com.nzefler.community.dto.CommunityRequestDTO;
import com.nzefler.community.dto.CommunityResponseDTO;
import com.nzefler.community.dto.CommunityUserResponseDTO;
import com.nzefler.community.dto.UserResponseDTO;


import java.util.List;

public interface CommunityService {

//    Basic CRUD
    List<CommunityResponseDTO> findAllCommunities();
    CommunityUserResponseDTO findCommunityById(Long communityId);
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
