package com.nzefler.auth.controller;

import com.nzefler.auth.dto.*;
import com.nzefler.auth.service.AuthServiceImpl;
import com.nzefler.auth.util.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthServiceImpl authService;

    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO request, HttpServletResponse response) {
        AuthResponseDTO authResponse = authService.login(request);
        CookieUtil.addRefreshTokenCookie(response, authResponse.getRefreshToken());
        authResponse.setRefreshToken(null);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refresh(@RequestBody(required = false) RefreshRequestDTO body, @CookieValue(name = "refreshToken", required = false) String cookieToken, HttpServletResponse response) {
        String refreshToken = (cookieToken != null) ? cookieToken : (body != null ? body.getRefreshToken() : null);

        if (refreshToken == null) {
            return ResponseEntity.badRequest().build();
        }
        AuthResponseDTO authResponse = authService.refresh(new RefreshRequestDTO(refreshToken));
        CookieUtil.addRefreshTokenCookie(response, authResponse.getRefreshToken());
        authResponse.setRefreshToken(null);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/validate")
    public ResponseEntity<TokenValidationResponseDTO> validate(@RequestBody TokenValidationRequestDTO request) {
        return ResponseEntity.ok(authService.validate(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        CookieUtil.clearRefreshTokenCookie(response);
        return ResponseEntity.noContent().build();
    }
}
