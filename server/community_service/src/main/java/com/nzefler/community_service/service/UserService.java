package com.nzefler.community_service.service;

import com.nzefler.community_service.dto.CommunityResponseDTO;
import com.nzefler.community_service.dto.UserRequestDTO;
import com.nzefler.community_service.dto.UserResponseDTO;
import com.nzefler.community_service.model.Community;
import com.nzefler.community_service.model.User;

import java.util.List;

public interface UserService {
//    Basic CRUD
    List<UserResponseDTO> findAllUsers();
    UserResponseDTO findUserById(Long userId);
    UserResponseDTO saveUser(UserRequestDTO user);
    UserResponseDTO updateUser(UserRequestDTO user);
    String deleteUser(Long userId);


//    Relationship methods
    Boolean joinCommunity(Long userId, Long communityId);
    Boolean leaveCommunity(Long userId, Long communityId);
    List<CommunityResponseDTO> findAllUserCommunities(Long userId);


//    Utility methods
    UserResponseDTO findUserByEmailId(String emailId);

}
