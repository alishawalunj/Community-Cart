package com.nzefler.community_service.service;

import com.nzefler.community_service.dto.UserDTO;
import com.nzefler.community_service.model.User;

import java.util.List;

public interface UserService {
    List<UserDTO> findAllUsers();
    UserDTO findUserById(Long userId);
    UserDTO findUserByEmailId(String emailId);
    List<UserDTO> findAllUsersByCommunityId(Long communityId);
    String saveUser(User user);
    User updateUser(User user);
    void deleteUser(Long userId);
}
