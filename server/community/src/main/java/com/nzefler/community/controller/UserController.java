package com.nzefler.community.controller;

import com.nzefler.community.dto.*;
import com.nzefler.community.model.User;
import com.nzefler.community.service.JwtService;
import com.nzefler.community.service.S3Service;
import com.nzefler.community.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userService;
    private final JwtService jwtService;
    private final S3Service s3Service;

    public UserController(UserServiceImpl userService, JwtService jwtService, S3Service s3Service) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.s3Service = s3Service;
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader){
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return ResponseEntity.status(401).body("Missing or Invalid Header");
        }
        String token = authHeader.substring(7);
        if(jwtService.isTokenValid(token)){
            String username = jwtService.extractUserName(token);
            return ResponseEntity.ok(username);
        }else{
            return ResponseEntity.status(401).body("Invalid token");
        }
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request){
        String email = request.getEmailId();
        String password = request.getPassword();
        User user = userService.authenticate(email, password);
        Long userId = user.getUserId();
        String token = jwtService.generateToken(user.getEmailId());
        System.out.println(userId);
        return new AuthResponse(userId, token);
    }

    @GetMapping("/all")
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

    @GetMapping("/users/{userId}/communityList")
    public ResponseEntity<List<Long>> getUserCommunityList(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.findUsersCommunitiesList(userId));
    }

    @PostMapping("/user/{userId}/upload-image")
    public ResponseEntity<String> uploadUserImage(@PathVariable Long userId, @RequestParam("file") MultipartFile file) throws IOException {
        String fileUrl = s3Service.uploadFile("users", userId + "-" + file.getOriginalFilename(), file.getBytes());
        userService.updateImageUrl(userId, fileUrl);
        return ResponseEntity.ok(fileUrl);
    }


}
