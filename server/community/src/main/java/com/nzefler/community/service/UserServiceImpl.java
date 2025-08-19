package com.nzefler.community.service;

import com.nzefler.community.dto.*;
import com.nzefler.community.exception.EntityAlreadyExistsException;
import com.nzefler.community.exception.EntityNotFoundException;
import com.nzefler.community.exception.ErrorMessages;
import com.nzefler.community.mapper.CommunityMapper;
import com.nzefler.community.mapper.UserMapper;
import com.nzefler.community.model.Community;
import com.nzefler.community.model.User;
import com.nzefler.community.repository.CommunityRepository;
import com.nzefler.community.repository.UserRepository;
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
            throw new RuntimeException(ErrorMessages.ERROR_IN_PROCESSING);
        }
    }

    @Override
    public UserCommunityResponseDTO findUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            throw new EntityNotFoundException(ErrorMessages.USER_NOT_FOUND);
        }
        User user = optionalUser.get();
        return userMapper.toEntity(user);
    }

    @Override
    @Transactional
    public UserResponseDTO saveUser(UserRequestDTO user) {
        if(userRepository.findByEmailId(user.getEmailId()).isPresent()) {
                throw new EntityAlreadyExistsException(ErrorMessages.USER_ALREADY_EXITS);
            }
            User newUser = userMapper.toEntity(user);
            userRepository.save(newUser);
            return userMapper.toDTO(newUser);
    }

    @Override
    @Transactional
    public UserResponseDTO updateUser(UserRequestDTO user) {
        Optional<User> existingUserOptional = userRepository.findByEmailId(user.getEmailId());
        if(existingUserOptional.isEmpty()){
                throw new EntityNotFoundException(ErrorMessages.USER_NOT_FOUND);
            }else{
                User existingUser = existingUserOptional.get();
                existingUser.setFirstName(user.getFirstName());
                existingUser.setLastName(user.getLastName());
                existingUser.setEmailId(user.getEmailId());
                existingUser.setPassword(user.getPassword());
                userRepository.save(existingUser);
                return userMapper.toDTO(existingUser);
            }
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        if(!userRepository.existsById(userId)){
            throw new EntityNotFoundException(ErrorMessages.USER_NOT_FOUND);
        }
        userRepository.deleteById(userId);
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
            throw new RuntimeException(ErrorMessages.ERROR_IN_PROCESSING);
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
            throw new RuntimeException(ErrorMessages.ERROR_IN_PROCESSING);
        }

    }

    @Override
    public List<CommunityResponseDTO> findAllUserCommunities(Long userId) {
        try{
            User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User doesnt exist") );
            Set<Community> communities= user.getCommunities();
            return communities.stream().map(communityMapper::toDTO).collect(Collectors.toList());
        }catch(Exception e){
            throw new RuntimeException(ErrorMessages.ERROR_IN_PROCESSING);
        }
    }

    @Override
    public UserResponseDTO findUserByEmailId(String emailId) {
        try{
            return userRepository.findByEmailId(emailId).map(userMapper::toDTO).orElseThrow(() -> new EntityNotFoundException("User doesnt exists"));
        }catch(Exception e){
            throw new RuntimeException(ErrorMessages.ERROR_IN_PROCESSING);
        }
    }

    @Override
    public User authenticate(String emailId, String password) {
        try{
            User user = userRepository.findByEmailId(emailId).orElseThrow(() -> new RuntimeException("User not found"));
            if(!user.getPassword().equals(password)){
                throw new RuntimeException("Invalid password");
            }
            return user;
        }catch(Exception e){
            throw new RuntimeException(ErrorMessages.ERROR_IN_PROCESSING);
        }
    }


}
