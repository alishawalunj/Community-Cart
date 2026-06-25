package com.nzefler.auth.service;

import com.nzefler.auth.dto.*;

public interface AuthService {
    AuthResponseDTO login(LoginRequestDTO request);
    AuthResponseDTO refresh(RefreshRequestDTO request);
    TokenValidationResponseDTO validate(TokenValidationRequestDTO request);
}
