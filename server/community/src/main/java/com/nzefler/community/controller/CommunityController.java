package com.nzefler.community.controller;

import com.nzefler.community.dto.CommunityRequestDTO;
import com.nzefler.community.dto.CommunityResponseDTO;
import com.nzefler.community.dto.UserRefDTO;
import com.nzefler.community.service.CommunityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class CommunityController {

    private final CommunityService communityService;

    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    private Long currentUserId() {
        return (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping("/communities")
    public ResponseEntity<List<CommunityResponseDTO>> findAll(){
        return ResponseEntity.ok(communityService.findAllCommunities());
    }

    @GetMapping("/communities/{communityId}")
    public ResponseEntity<CommunityResponseDTO> findById(@PathVariable Long communityId){
        return ResponseEntity.ok(communityService.findById(communityId));
    }

    @GetMapping("/communities/explore")
    public ResponseEntity<List<CommunityResponseDTO>> explore() {
        return ResponseEntity.ok(communityService.explore(currentUserId()));
    }
    @PostMapping("/communities")
    public ResponseEntity<CommunityResponseDTO> create(@RequestBody CommunityRequestDTO request){
        return ResponseEntity.status(HttpStatus.CREATED).body(communityService.create(currentUserId(), request));
    }

    @PutMapping("/communities/{communityId}")
    public ResponseEntity<CommunityResponseDTO> update(@PathVariable Long communityId, @RequestBody CommunityRequestDTO request){
        return ResponseEntity.ok(communityService.update(communityId, currentUserId(), request));
    }

    @DeleteMapping("/communities/{communityId}")
    public ResponseEntity<Void> delete( @PathVariable Long communityId){
        communityService.delete(communityId, currentUserId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/communities/{communityId}/members/{userId}")
    public ResponseEntity<Void> join(@PathVariable Long communityId, @PathVariable Long userId){
        communityService.addMember(communityId, userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/communities/{communityId}/members/{userId}")
    public ResponseEntity<Void> leave(@PathVariable Long communityId, @PathVariable Long userId){
        communityService.removeMember(communityId, userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/communities/{communityId}/members")
    public ResponseEntity<List<UserRefDTO>> getMembers(@PathVariable Long communityId){
        return ResponseEntity.ok(communityService.getMembers(communityId));
    }

    @GetMapping("/users/{userId}/communities")
    public ResponseEntity<List<Long>> getUserCommunityIds(@PathVariable Long userId) {
        return ResponseEntity.ok(communityService.getUserCommunityIds(userId));
    }
}

