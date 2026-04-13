package com.nzefler.community.service;

import com.nzefler.community.dto.CommunityRequestDTO;
import com.nzefler.community.dto.CommunityResponseDTO;
import com.nzefler.community.dto.UserRefDTO;

import java.util.List;

public interface CommunityService {

//    Basic CRUD
    List<CommunityResponseDTO> findAllCommunities();
    CommunityResponseDTO findById(Long communityId);
    CommunityResponseDTO create(Long ownerId, CommunityRequestDTO request);
    CommunityResponseDTO update(Long communityId, Long requestingUserId, CommunityRequestDTO request);
    void delete(Long communityId, Long requestingUserId);

//    Membership
    void addMember(Long communityId, Long userId);
    void removeMember(Long communityId, Long userId);
    List<UserRefDTO> getMembers(Long communityId);

//    Utility methods
    List<CommunityResponseDTO> explore(Long userId);

//    Inter service
    List<Long> getUserCommunityIds(Long userId);
}
