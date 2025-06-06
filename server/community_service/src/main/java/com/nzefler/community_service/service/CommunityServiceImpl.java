package com.nzefler.community_service.service;

import com.nzefler.community_service.dto.CommunityRequestDTO;
import com.nzefler.community_service.dto.CommunityResponseDTO;
import com.nzefler.community_service.dto.UserResponseDTO;
import com.nzefler.community_service.exception.EntityAlreadyExistsException;
import com.nzefler.community_service.exception.EntityNotFoundException;
import com.nzefler.community_service.mapper.CommunityMapper;
import com.nzefler.community_service.mapper.UserMapper;
import com.nzefler.community_service.model.Community;
import com.nzefler.community_service.model.User;
import com.nzefler.community_service.repository.CommunityRepository;
import com.nzefler.community_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommunityServiceImpl implements CommunityService{

    private final CommunityRepository communityRepository;
    private final CommunityMapper mapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public CommunityServiceImpl(CommunityRepository communityRepository, CommunityMapper mapper, UserRepository userRepository,UserMapper userMapper) {
        this.communityRepository = communityRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<CommunityResponseDTO> findAllCommunities() {
        try{
            return communityRepository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
        }catch(Exception e){
            throw new RuntimeException("Error fetching communities");
        }
    }
    @Transactional
    @Override
    public CommunityResponseDTO findCommunityById(Long communityId) {
        try{
            return communityRepository.findByIdWithUsers(communityId).map(mapper::toDTO).orElseThrow(() -> new EntityNotFoundException("Community doesnt exists"));
        }catch(Exception e){
            throw new RuntimeException("Error in fetching Community details");
        }
    }

    @Override
    @Transactional
    public CommunityResponseDTO saveCommunity(CommunityRequestDTO community) {
        try{
            if(communityRepository.findByName(community.getName()).isPresent()){
                throw new EntityAlreadyExistsException("Community Already exists");
            }
            Community newCommunity = mapper.toEntity(community);
            communityRepository.save(newCommunity);
//            return communityRepository.findByName(community.getName());
            return mapper.toDTO(newCommunity);
        }catch(Exception e){
            throw new RuntimeException("Error in creating community");
        }
    }

    @Override
    @Transactional
    public CommunityResponseDTO updateCommunity(CommunityRequestDTO community) {
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
            return mapper.toDTO(existingCommunity);
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
    public Boolean addUsersToCommunity(Long communityId, Long userId) {
        try{
            Community community = communityRepository.findById(communityId).orElseThrow(() -> new EntityNotFoundException("Community doesn't exists"));
            User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User does not exist"));
            community.getUsers().add(user);
            user.getCommunities().add(community);
            userRepository.save(user);
            return true;
        }catch(EntityNotFoundException e ){
            throw e;
        }catch(Exception e){
            throw new RuntimeException("Error in updating Community");
        }
    }

    @Override
    @Transactional
    public Boolean removeUsersFromCommunity(Long communityId, Long userId) {
        try{
            Community community = communityRepository.findById(communityId).orElseThrow(() -> new EntityNotFoundException("Community doesn't exists"));
            User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User does not exist"));
            community.getUsers().remove(user);
            user.getCommunities().remove(community);
            userRepository.save(user);
            return true;
        }catch(EntityNotFoundException e ){
            throw e;
        }catch(Exception e ){
            throw new RuntimeException("Error in updating Community");
        }

    }

    @Override
    public List<UserResponseDTO> findAllCommunityUsers(Long communityId) {
        try{
            Community community = communityRepository.findById(communityId).orElseThrow(() -> new EntityNotFoundException("community does not exist"));
            Set<User> users = community.getUsers();
            return users.stream().map(userMapper::toDTO).collect(Collectors.toList());
        }catch (Exception e){
            throw new RuntimeException("Error in updating Community");
        }
    }

    @Override
    public CommunityResponseDTO findCommunityByName(String name) {
        try{
            return communityRepository.findByName(name).map(mapper::toDTO).orElseThrow(() -> new EntityNotFoundException("Community doesnt exists"));
        }catch (Exception e){
            throw new RuntimeException("Error in fetching Community details");
        }
    }
}
