package com.nzefler.community_service.service;

import com.nzefler.community_service.dto.UserDTO;
import com.nzefler.community_service.exception.EntityAlreadyExistsException;
import com.nzefler.community_service.exception.EntityNotFoundException;
import com.nzefler.community_service.mapper.UserMapper;
import com.nzefler.community_service.model.Community;
import com.nzefler.community_service.model.User;
import com.nzefler.community_service.repository.CommunityRepository;
import com.nzefler.community_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CommunityRepository communityRepository;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, CommunityRepository communityRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.communityRepository = communityRepository;
    }

    @Override
    public List<UserDTO> findAllUsers() {
        try{
            return userRepository.findAll().stream().map(userMapper::toDTO).collect(Collectors.toList());
        }catch (Exception e){
            throw new RuntimeException("Error fetching users details");
        }
    }

    @Override
    public UserDTO findUserById(Long userId) {
        try{
            return userRepository.findById(userId).map(userMapper::toDTO).orElseThrow(() -> new EntityNotFoundException("User doesnt exists"));
        }catch(Exception e){
            throw new RuntimeException("Error fetching users details");
        }
    }

    @Override
    public UserDTO findUserByEmailId(String emailId) {
        try{
            return userRepository.findByEmailId(emailId).map(userMapper::toDTO).orElseThrow(() -> new EntityNotFoundException("User doesnt exists"));
        }catch(Exception e){
            throw new RuntimeException("Error fetching users details");
        }
    }


    @Override
    public List<UserDTO> findAllUsersByCommunityId(Long communityId) {
        try{
            return userRepository.findByCommunity_CommunityId(communityId).stream().map(userMapper::toDTO).collect(Collectors.toList());
        }catch(Exception e){
            throw new RuntimeException("Error fetching users");
        }
    }

    @Override
    @Transactional
    public String saveUser(User user) {
        try{
            if(userRepository.findByEmailId(user.getEmailId()).isPresent()) {
                throw new EntityAlreadyExistsException("User Already exists");
            }
            userRepository.save(user);
            return "New User created successfully";
        }catch(Exception e){
            throw new RuntimeException("Error in user creation");
        }
    }

    @Override
    @Transactional
    public User updateUser(User user) {
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
                return existingUser;
            }
        }catch(Exception e){
            throw new RuntimeException("Failed to update the user");
        }
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        try{
            userRepository.deleteById(userId);
        }catch (Exception e) {
            throw new RuntimeException("Failed to delete user");
        }
    }

    @Override
    @Transactional
    public UserDTO updateUserCommunities(Long userId, List<Long> communityIds) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User doesnt exists"));
        List<Community> communities = communityRepository.findAllById(communityIds);
        if (communities.size() != communityIds.size()) {
            throw new EntityNotFoundException("One or more communities not found");
        }
        user.setCommunities(new HashSet<>(communities));
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

}
