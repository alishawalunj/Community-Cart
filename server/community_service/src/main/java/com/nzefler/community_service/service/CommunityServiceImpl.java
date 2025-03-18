package com.nzefler.community_service.service;

import com.nzefler.community_service.dto.CommunityResponseDTO;
import com.nzefler.community_service.mapper.CommunityMapper;
import com.nzefler.community_service.model.Community;
import com.nzefler.community_service.repository.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class CommunityServiceImpl implements CommunityService{

    @Autowired
    private CommunityRepository communityRepository;

    @Autowired
    private CommunityMapper mapper;

    @Override
    public List<CommunityResponseDTO> findAllCommunities() {
        try{
            List<CommunityResponseDTO> communitiesList = new ArrayList<>();
            List<Community> communities = communityRepository.findAll();
            for(Community community : communities){
                communitiesList.add(mapper.toDTO(community));
            }
            return communitiesList;
        }catch(Exception e){
            throw new RuntimeException("Error fetching communities");
        }
    }

    @Override
    public CommunityResponseDTO findCommunityById(Long communityId) {
        try{
            return communityRepository.findById(communityId).map(mapper::toDTO).orElseThrow(() -> new RuntimeException("Community not found in database"));
        }catch(Exception e){
            throw new RuntimeException("Error in fetching Community details");
        }
    }

    @Override
    public CommunityResponseDTO findCommunityByName(String name) {
        Optional<Community> existingCommunity = communityRepository.findByName(name);
        if(existingCommunity.isEmpty()){
            throw new RuntimeException("Community not found");
        }
        return existingCommunity.map(mapper::toDTO).orElseThrow(() -> new RuntimeException("Error fetching Community details"));
    }

    @Override
    public String saveCommunity(Community community) {
        try{
            if(communityRepository.findByName(community.getName()).isPresent()){
                throw new RuntimeException("Community already exists");
            }else{
                communityRepository.save(community);
                return "Community created successfully!!";
            }
        }catch(Exception e){
            throw new RuntimeException("Error in creating community");
        }
    }

    @Override
    public Community updateCommunity(Community community) {
         Optional<Community> optionalCommunity = communityRepository.findByName(community.getName());
         if(optionalCommunity.isEmpty()){
             throw new RuntimeException("Community doesnt exist, create it first ");
         }
         Community existingCommunity = optionalCommunity.get();
         existingCommunity.setDescription(community.getDescription());
         existingCommunity.setName(community.getName());
         existingCommunity.setOwner(community.getOwner());
//         TODO: use of updating members List
//        existingCommunity.setMembersList(community.getMembersList());
        communityRepository.save(existingCommunity);
        return existingCommunity;
    }

    @Override
    public void deleteCommunity(Long communityId) {
        try{
            communityRepository.deleteById(communityId);
        }catch(Exception e){
            throw new RuntimeException("Failed to delete the community");
        }

    }
}
