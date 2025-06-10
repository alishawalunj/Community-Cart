package com.nzefler.community.service;

import com.nzefler.community.dto.CommunityResponseDTO;
import com.nzefler.community.dto.UserRequestDTO;
import com.nzefler.community.dto.UserResponseDTO;
import com.nzefler.community.model.Community;
import com.nzefler.community.model.User;

import java.util.List;

public interface UserService {
//    Basic CRUD
    List<UserResponseDTO> findAllUsers();
    UserResponseDTO findUserById(Long userId);
    UserResponseDTO saveUser(UserRequestDTO user);
    UserResponseDTO updateUser(UserRequestDTO user);
    void deleteUser(Long userId);


//    Relationship methods
    Boolean joinCommunity(Long userId, Long communityId);
    Boolean leaveCommunity(Long userId, Long communityId);
    List<CommunityResponseDTO> findAllUserCommunities(Long userId);


//    Utility methods
    UserResponseDTO findUserByEmailId(String emailId);

}
