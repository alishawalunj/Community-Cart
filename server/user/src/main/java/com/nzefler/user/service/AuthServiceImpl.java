package com.nzefler.user.service;

import com.nzefler.user.dto.AuthResponseDTO;
import com.nzefler.user.dto.LoginRequestDTO;
import com.nzefler.user.enums.ErrorConstants;
import com.nzefler.user.exception.EntityNotFoundException;
import com.nzefler.user.model.User;
import com.nzefler.user.repository.UserRepository;
import com.nzefler.user.security.JwtUtil;
import com.nzefler.user.util.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpHeaders;
import java.util.Arrays;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final CookieUtil cookieUtil;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository, CookieUtil cookieUtil, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.cookieUtil = cookieUtil;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO requestDTO, HttpServletResponse response) {

        User user = userRepository.findByEmail(requestDTO.getEmail())
                .orElseThrow(() -> new EntityNotFoundException(ErrorConstants.USER_NOT_FOUND));
        if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        String accessToken = jwtUtil.generateToken(user.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());
        response.addHeader(HttpHeaders.SET_COOKIE, cookieUtil.createRefreshCookie(refreshToken).toString());
        return new AuthResponseDTO(
                user.getUserId(),
                accessToken
        );
    }

    @Override
    public AuthResponseDTO refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = extractRefreshToken(request);

        String email = jwtUtil.extractEmail(refreshToken);

        if (!jwtUtil.validateToken(refreshToken, email)) {
            throw new RuntimeException("Invalid Refresh token");
        }
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        String newAccessToken = jwtUtil.generateToken(email);
        return new AuthResponseDTO(user.getUserId(), newAccessToken);
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        response.addHeader(HttpHeaders.SET_COOKIE, cookieUtil.deleteRefreshCookie().toString());
    }

    private String extractRefreshToken(HttpServletRequest request){
        if(request.getCookies() == null){
            throw new RuntimeException("no cookies found");
        }
        return Arrays.stream(request.getCookies()).filter(c -> c.getName().equals("refreshToken"))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));
    }
}