package com.nzefler.community_service.service;

import com.nzefler.community_service.dto.CommunityDTO;
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

    @Override
    public CommunityDTO findCommunityById(Long communityId) {
        try{
            return communityRepository.findById(communityId).map(mapper::toDTO).orElseThrow(() -> new EntityNotFoundException("Community doesnt exists"));
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
    public CommunityDTO addUsersToCommunity(Long communityId, List<Long> userIds) {
        Community community = communityRepository.findById(communityId).orElseThrow(() -> new EntityNotFoundException("Community doesn't exists"));
        List<User> users = userRepository.findAllById(userIds);
        if(users.size() != userIds.size()){
            throw new EntityNotFoundException("One or more user doesnt exists");
        }
        community.getUsers().addAll(users);
        communityRepository.save(community);
        return mapper.toDTO(community);
    }

    @Override
    @Transactional
    public CommunityDTO removeUsersFromCommunity(Long communityId, List<Long> userIds) {
        Community community = communityRepository.findById(communityId).orElseThrow(() -> new EntityNotFoundException("Community doesn't exists"));
        List<User> users = userRepository.findAllById(userIds);
        users.forEach(community.getUsers()::remove);
        communityRepository.save(community);
        return mapper.toDTO(community);
    }
}
