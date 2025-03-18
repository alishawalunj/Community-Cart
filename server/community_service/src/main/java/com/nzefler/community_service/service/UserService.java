package com.nzefler.community_service.service;

import com.nzefler.community_service.dto.UserResponseDTO;
import com.nzefler.community_service.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAllUsers();
    UserResponseDTO findUserById(Long userId);
    UserResponseDTO findUserByEmailId(String emailId);
    String saveUser(User user);
    User updateUser(User user);
    void deleteUser(Long userId);
}
