package com.nzefler.community.controller;

import com.nzefler.community.dto.CommunityRequestDTO;
import com.nzefler.community.dto.CommunityResponseDTO;
import com.nzefler.community.dto.CommunityUserResponseDTO;
import com.nzefler.community.dto.UserResponseDTO;
import com.nzefler.community.service.CommunityServiceImpl;
import com.nzefler.community.service.S3Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/community-service")
public class CommunityController {

    private final CommunityServiceImpl communityService;
    private final S3Service s3Service;

    public CommunityController(CommunityServiceImpl communityService, S3Service s3Service) {
        this.communityService = communityService;
        this.s3Service = s3Service;
    }

    @GetMapping("/community/all")
    public List<CommunityResponseDTO> getAllCommunities(){
        return communityService.findAllCommunities();
    }

    @GetMapping("/getCommunityById/{communityId}")
    public ResponseEntity<CommunityUserResponseDTO> getCommunityById(@PathVariable Long communityId){
        CommunityUserResponseDTO response = communityService.findCommunityById(communityId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getCommunityByName/{name}")
    public ResponseEntity<CommunityResponseDTO> getCommunityByName( @PathVariable String name){
        CommunityResponseDTO response =  communityService.findCommunityByName(name);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/createCommunity")
    public ResponseEntity<CommunityResponseDTO> createCommunity(@RequestBody CommunityRequestDTO dto){
        CommunityResponseDTO response = communityService.saveCommunity(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/updateCommunity")
    public ResponseEntity<CommunityResponseDTO> updateCommunity(@RequestBody CommunityRequestDTO dto){
        CommunityResponseDTO response = communityService.updateCommunity(dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteCommunity/{communityId}")
    public ResponseEntity<Void> deleteCommunity( @PathVariable Long communityId){
        communityService.deleteCommunity(communityId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/addUsersToCommunity/communityId/{communityId}/userId/{userId}")
    public ResponseEntity<Boolean> addUsersToCommunity(@PathVariable Long communityId, @PathVariable  Long userId){
        boolean result = communityService.addUsersToCommunity(communityId, userId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/removeUsersFromCommunity/communityId/{communityId}/userId/{userId}")
    public ResponseEntity<Boolean> removeUsersFromCommunity( @PathVariable Long communityId,  @PathVariable Long userId){
        boolean result = communityService.removeUsersFromCommunity(communityId, userId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAllCommunityUsers/{communityId}")
    public List<UserResponseDTO> getAllCommunityUsers(@PathVariable Long communityId){
        return communityService.findAllCommunityUsers(communityId);
    }

    @GetMapping("/communities/{userId}/owned")
    public List<CommunityResponseDTO> getUserOwnedCommunities(@PathVariable Long userId) {
        return communityService.findAllUserOwnedCommunities(userId);
    }

    //image upload
    @PostMapping("/{communityId}/upload-image")
    public ResponseEntity<String> uploadCommunityImage(@PathVariable Long communityId, @RequestParam("file") MultipartFile file) throws IOException {
        String fileUrl = s3Service.uploadFile("community", communityId + "-" + file.getOriginalFilename(), file.getBytes());
        communityService.updateImageUrl(communityId, fileUrl);
        return ResponseEntity.ok(fileUrl);
    }
}

