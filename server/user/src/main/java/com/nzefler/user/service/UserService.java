package com.nzefler.user.service;

import com.nzefler.user.dto.UserRequestDTO;
import com.nzefler.user.dto.UserResponseDTO;

public interface UserService {
    UserResponseDTO register(UserRequestDTO request);
    UserResponseDTO findById(Long userId);
    UserResponseDTO update(Long userId, UserRequestDTO request);
    void delete(Long userId);
    UserResponseDTO findByEmail(String email);
}
