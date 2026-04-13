package com.nzefler.user.service;

import com.nzefler.user.dto.AuthResponseDTO;
import com.nzefler.user.dto.LoginRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    AuthResponseDTO login(LoginRequestDTO requestDTO, HttpServletResponse response);
    AuthResponseDTO refresh(HttpServletRequest request, HttpServletResponse response);
    void logout(HttpServletRequest request, HttpServletResponse response);
}
