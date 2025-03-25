package com.nzefler.community_service.service;

import com.nzefler.community_service.dto.CommunityDTO;
import com.nzefler.community_service.dto.CommunityDetailDTO;
import com.nzefler.community_service.exception.EntityAlreadyExistsException;
import com.nzefler.community_service.exception.EntityNotFoundException;
import com.nzefler.community_service.mapper.CommunityMapper;
import com.nzefler.community_service.model.Community;
import com.nzefler.community_service.model.User;
import com.nzefler.community_service.repository.CommunityRepository;
import com.nzefler.community_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommunityServiceImpl implements CommunityService{

    private final CommunityRepository communityRepository;
    private final CommunityMapper mapper;
    private final UserRepository userRepository;

    public CommunityServiceImpl(CommunityRepository communityRepository, CommunityMapper mapper, UserRepository userRepository) {
        this.communityRepository = communityRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    @Override
    public List<CommunityDTO> findAllCommunities() {
        try{
            return communityRepository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
        }catch(Exception e){
            throw new RuntimeException("Error fetching communities");
        }
    }
    @Transactional
    @Override
    public CommunityDetailDTO findCommunityById(Long communityId) {
        try{
            return communityRepository.findByIdWithUsers(communityId).map(mapper::toCommunityDetailDTO).orElseThrow(() -> new EntityNotFoundException("Community doesnt exists"));
        }catch(Exception e){
            throw new RuntimeException("Error in fetching Community details");
        }
    }

    @Override
    public CommunityDTO findCommunityByName(String name) {
        try{
            return communityRepository.findByName(name).map(mapper::toDTO).orElseThrow(() -> new EntityNotFoundException("Community doesnt exists"));
        }catch (Exception e){
            throw new RuntimeException("Error in fetching Community details");
        }
    }

    @Override
    @Transactional
    public String saveCommunity(Community community) {
        try{
            if(communityRepository.findByName(community.getName()).isPresent()){
                throw new EntityAlreadyExistsException("Community Already exists");
            }
            communityRepository.save(community);
            return "Community created successfully!!";
        }catch(Exception e){
            throw new RuntimeException("Error in creating community");
        }
    }

    @Override
    @Transactional
    public Community updateCommunity(Community community) {
        Optional<Community> optionalCommunity = communityRepository.findByName(community.getName());
        try{
            if(optionalCommunity.isEmpty()){
                throw new EntityNotFoundException("Community doesnt exists");
            }
            Community existingCommunity = optionalCommunity.get();
            existingCommunity.setDescription(community.getDescription());
            existingCommunity.setName(community.getName());
            existingCommunity.setOwner(community.getOwner());
            communityRepository.save(existingCommunity);
            return existingCommunity;
        }catch(Exception e){
            throw new RuntimeException("Error in updating Community");
        }
    }

    @Override
    @Transactional
    public void deleteCommunity(Long communityId) {
        try{
            communityRepository.deleteById(communityId);
        }catch(Exception e){
            throw new RuntimeException("Failed to delete the community");
        }
    }

    @Override
    @Transactional
    public CommunityDetailDTO addUsersToCommunity(Long communityId, Long userId) {
        try{
            Community community = communityRepository.findById(communityId).orElseThrow(() -> new EntityNotFoundException("Community doesn't exists"));
            User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User does not exist"));
            community.getUsers().add(user);
            user.getCommunities().add(community);
            userRepository.save(user);
            return mapper.toCommunityDetailDTO(community);
        }catch(Exception e){
            throw new RuntimeException("Error in updating Community");
        }
    }

    @Override
    @Transactional
    public CommunityDetailDTO removeUsersFromCommunity(Long communityId, Long userId) {
        Community community = communityRepository.findById(communityId).orElseThrow(() -> new EntityNotFoundException("Community doesn't exists"));
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User does not exist"));
        community.getUsers().remove(user);
        user.getCommunities().remove(community);
        userRepository.save(user);
        return mapper.toCommunityDetailDTO(community);
    }
}
