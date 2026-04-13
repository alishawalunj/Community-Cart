package com.nzefler.user.controller;

import com.nzefler.user.dto.AuthResponseDTO;
import com.nzefler.user.dto.LoginRequestDTO;
import com.nzefler.user.dto.UserRequestDTO;
import com.nzefler.user.dto.UserResponseDTO;
import com.nzefler.user.service.AuthService;
import com.nzefler.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserAuthController {

    private final UserService userService;
    private final AuthService authService;

    public UserAuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO request, HttpServletResponse response){
        AuthResponseDTO responseDto = authService.login(request, response);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response){
        authService.logout(request, response);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refresh(HttpServletRequest request, HttpServletResponse response){
        AuthResponseDTO responseDto = authService.refresh(request, response);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/save")
    public ResponseEntity<UserResponseDTO> saveUser(@RequestBody UserRequestDTO request){
        UserResponseDTO responseDTO = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<UserResponseDTO> updateUser(@RequestParam Long userId, @RequestBody UserRequestDTO request){
        UserResponseDTO responseDTO = userService.update(userId, request);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/id/{userId}")
    public ResponseEntity<UserResponseDTO> fetchUserById(@PathVariable Long userId){
        UserResponseDTO responseDTO = userService.findById(userId);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDTO> fetchUserByEmail(@PathVariable String email){
        UserResponseDTO responseDTO = userService.findByEmail(email);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId){
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }

}
