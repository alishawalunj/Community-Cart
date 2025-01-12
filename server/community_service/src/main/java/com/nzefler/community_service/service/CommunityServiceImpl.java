package com.nzefler.community_service.service;

import com.nzefler.community_service.model.Community;
import com.nzefler.community_service.repository.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CommunityServiceImpl implements CommunityService{

    @Autowired
    private CommunityRepository communityRepository;

    @Override
    public List<Community> getAllCommunities() {
        return communityRepository.findAll();
    }

    @Override
    public Optional<Community> getCommunityById(Long communityId) {
        return communityRepository.findById(communityId);
    }

    @Override
    public Community createCommunity(Community community) {
        if(community.getCommunityId() != null){
            throw new RuntimeException("Community already exists, please create a new one");
        }
        return communityRepository.save(community);
    }

    @Override
    public Community updateCommunity(Community community) {
         Optional<Community> optionalCommunity = communityRepository.findById(community.getCommunityId());
         if(optionalCommunity.isEmpty()){
             throw new RuntimeException("Community doesnt exist, create it first ");
         }
         Community existingCommunity = optionalCommunity.get();
         existingCommunity.setDescription(community.getDescription());
         existingCommunity.setName(community.getName());
         existingCommunity.setOwner(community.getOwner());
//         TODO: use of updating members List
//        existingCommunity.setMembersList(community.getMembersList());
        return communityRepository.save(existingCommunity);
    }

    @Override
    public void deleteCommunity(Long communityId) {
        communityRepository.deleteById(communityId);
    }
}
