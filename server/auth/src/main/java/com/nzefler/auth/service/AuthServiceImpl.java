package com.nzefler.auth.service;

import com.nzefler.auth.dto.*;
import com.nzefler.auth.security.CustomUserDetails;
import com.nzefler.auth.security.CustomUserDetailsService;
import com.nzefler.auth.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String accessToken  = jwtUtil.generateToken(userDetails.getEmail(), userDetails.getUserId(), userDetails.getRole());
        String refreshToken = jwtUtil.generateRefreshToken(userDetails.getEmail());
        return new AuthResponseDTO(userDetails.getUserId(), accessToken, refreshToken);
    }

    @Override
    public AuthResponseDTO refresh(RefreshRequestDTO request) {
        String refreshToken = request.getRefreshToken();
        if (!jwtUtil.isTokenStructurallyValid(refreshToken)) {
            throw new IllegalArgumentException("Invalid or expired refresh token");
        }
        String email = jwtUtil.extractEmail(refreshToken);
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(email);
        String newAccessToken  = jwtUtil.generateToken(userDetails.getEmail(), userDetails.getUserId(), userDetails.getRole());
        String newRefreshToken = jwtUtil.generateRefreshToken(userDetails.getEmail());
        return new AuthResponseDTO(userDetails.getUserId(), newAccessToken, newRefreshToken);
    }

    @Override
    public TokenValidationResponseDTO validate(TokenValidationRequestDTO request) {
        String token = request.getToken();
        try {
            if (!jwtUtil.isTokenStructurallyValid(token)) {
                return new TokenValidationResponseDTO(false, null, null, null);
            }
            return new TokenValidationResponseDTO(
                    true,
                    jwtUtil.extractUserId(token),
                    jwtUtil.extractEmail(token),
                    jwtUtil.extractRole(token)
            );
        } catch (Exception e) {
            return new TokenValidationResponseDTO(false, null, null, null);
        }
    }
}
