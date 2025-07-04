package com.nzefler.community.controller;

import com.nzefler.community.dto.*;
import com.nzefler.community.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/community-service")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/users/all")
    public List<UserResponseDTO> getAllUsers(){
        return userService.findAllUsers();
    }

    @GetMapping("/getUserById/{userId}")
    public ResponseEntity<UserCommunityResponseDTO> getUserById(@PathVariable  Long userId){
        UserCommunityResponseDTO response = userService.findUserById(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getUserByEmailId/{emailId}")
    public ResponseEntity<UserResponseDTO> getUserByEmailId(@PathVariable  String emailId){
        UserResponseDTO response = userService.findUserByEmailId(emailId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody AuthRequestDTO requestDTO){
        UserResponseDTO response = userService.login(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/createUser")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO){
        UserResponseDTO response = userService.saveUser(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UserRequestDTO userRequestDTO){
        UserResponseDTO response = userService.updateUser(userRequestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/users/join")
    public ResponseEntity<Boolean> joinCommunity(@RequestParam Long userId, @RequestParam Long communityId) {
        return ResponseEntity.ok(userService.joinCommunity(userId, communityId));
    }

    @PostMapping("/users/leave")
    public ResponseEntity<Boolean> leaveCommunity(@RequestParam Long userId, @RequestParam Long communityId) {
        return ResponseEntity.ok(userService.leaveCommunity(userId, communityId));
    }

    @GetMapping("/users/{userId}/communities")
    public ResponseEntity<List<CommunityResponseDTO>> getUserCommunities(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.findAllUserCommunities(userId));
    }

}
