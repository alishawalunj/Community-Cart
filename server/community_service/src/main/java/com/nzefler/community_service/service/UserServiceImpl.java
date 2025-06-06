package com.nzefler.community_service.service;

import com.nzefler.community_service.dto.CommunityResponseDTO;
import com.nzefler.community_service.dto.UserRequestDTO;
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

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CommunityRepository communityRepository;
    private final CommunityMapper communityMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, CommunityRepository communityRepository, CommunityMapper communityMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.communityRepository = communityRepository;
        this.communityMapper = communityMapper;
    }

    @Override
    public List<UserResponseDTO> findAllUsers() {
        try{
            return userRepository.findAll().stream().map(userMapper::toDTO).collect(Collectors.toList());
        }catch (Exception e){
            throw new RuntimeException("Error fetching users details");
        }
    }

    @Override
    public UserResponseDTO findUserById(Long userId) {
        try{
            return userRepository.findById(userId).map(userMapper::toDTO).orElseThrow(() -> new EntityNotFoundException("User doesnt exists"));
        }catch(Exception e){
            throw new RuntimeException("Error fetching users details");
        }
    }

    @Override
    @Transactional
    public UserResponseDTO saveUser(UserRequestDTO user) {
        try{
            if(userRepository.findByEmailId(user.getEmailId()).isPresent()) {
                throw new EntityAlreadyExistsException("User Already exists");
            }
            User newUser = userMapper.toEntity(user);
            userRepository.save(newUser);
            return userMapper.toDTO(newUser);
        }catch(Exception e){
            throw new RuntimeException("Error in user creation");
        }
    }

    @Override
    @Transactional
    public UserResponseDTO updateUser(UserRequestDTO user) {
        Optional<User> existingUserOptional = userRepository.findByEmailId(user.getEmailId());
        try{
            if(existingUserOptional.isEmpty()){
                throw new EntityNotFoundException("User doesnt exists");
            }else{
                User existingUser = existingUserOptional.get();
                existingUser.setFirstName(user.getFirstName());
                existingUser.setLastName(user.getLastName());
                existingUser.setEmailId(user.getEmailId());
                existingUser.setPassword(user.getPassword());
                userRepository.save(existingUser);
                return userMapper.toDTO(existingUser);
            }
        }catch(Exception e){
            throw new RuntimeException("Failed to update the user");
        }
    }

    @Override
    @Transactional
    public String deleteUser(Long userId) {
        try{
            userRepository.deleteById(userId);
            return " User deleted successfully";
        }catch (Exception e) {
            throw new RuntimeException("Failed to delete user");
        }
    }

    @Override
    public Boolean joinCommunity(Long userId, Long communityId) {
        try{
            User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
            Community community = communityRepository.findById(communityId).orElseThrow(() -> new EntityNotFoundException("Community not found"));
            if(!user.getCommunities().contains(community)){
                user.getCommunities().add(community);
                community.getUsers().add(user);
                userRepository.save(user);
                communityRepository.save(community);
            }
            return true;
        }catch(Exception e) {
            throw new RuntimeException("Failed to delete user");
        }

    }

    @Override
    public Boolean leaveCommunity(Long userId, Long communityId) {
        try{
            User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
            Community community = communityRepository.findById(communityId).orElseThrow(() -> new EntityNotFoundException("Community not found"));
            if(user.getCommunities().contains(community)){
                user.getCommunities().remove(community);
                community.getUsers().remove(user);
                userRepository.save(user);
                communityRepository.save(community);
            }
            return true;
        }catch(Exception e) {
            throw new RuntimeException("Failed to delete user");
        }

    }

    @Override
    public List<CommunityResponseDTO> findAllUserCommunities(Long userId) {
        try{
            User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User doesnt exist") );
            Set<Community> communities= user.getCommunities();
            return communities.stream().map(communityMapper::toDTO).collect(Collectors.toList());
        }catch(Exception e){
            throw new RuntimeException("Error fetching communities");
        }
    }

    @Override
    public UserResponseDTO findUserByEmailId(String emailId) {
        try{
            return userRepository.findByEmailId(emailId).map(userMapper::toDTO).orElseThrow(() -> new EntityNotFoundException("User doesnt exists"));
        }catch(Exception e){
            throw new RuntimeException("Error fetching users details");
        }
    }



}
