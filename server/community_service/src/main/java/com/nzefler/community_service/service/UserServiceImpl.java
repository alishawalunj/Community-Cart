package com.nzefler.community_service.service;

import com.nzefler.community_service.dto.CommunityDTO;
import com.nzefler.community_service.dto.UserDTO;
import com.nzefler.community_service.mapper.UserMapper;
import com.nzefler.community_service.model.Community;
import com.nzefler.community_service.model.User;
import com.nzefler.community_service.repository.CommunityRepository;
import com.nzefler.community_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommunityRepository communityRepository;

    @Override
    public List<UserDTO> findAllUsers() {
        try{
            List<UserDTO> userResponseList = new ArrayList<>();
            List<User> users = userRepository.findAll();
            for(User user : users){
                userResponseList.add((userMapper.toDTO(user)));
            }
            return userResponseList;
        }catch (Exception e){
            throw new RuntimeException("Error fetching users details");
        }
    }

    @Override
    public UserDTO findUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new RuntimeException("No user Found with given user id");
        }
        return user.map(userMapper::toDTO).orElseThrow(() -> new RuntimeException("Error fetching user"));
    }

    @Override
    public UserDTO findUserByEmailId(String emailId) {
        Optional<User> existingUser = userRepository.findByEmailId(emailId);
        if(existingUser.isEmpty()){
            throw new RuntimeException("User is not the existing one");
        }
        return existingUser.map(userMapper::toDTO).orElseThrow(() -> new RuntimeException("Error fetching user"));
    }

    @Override
    public List<UserDTO> findAllUsersByCommunityId(Long communityId) {
        try{
            List<UserDTO> usersList = new ArrayList<>();
            List<User> users = userRepository.findByCommunity_CommunityId(communityId);
            for(User user : users){
                usersList.add(userMapper.toDTO(user));
            }
            return usersList;
        }catch(Exception e){
            throw new RuntimeException("Error fetching users");
        }
    }

    @Override
    public String saveUser(User user) {
        if(userRepository.findByEmailId(user.getEmailId()).isEmpty()) {
            userRepository.save(user);
            return "New User created successfully";
        }
        return "Error in user creation";
    }

    @Override
    public User updateUser(User user) {
        try{
            Optional<User> existingUserOptional = userRepository.findByEmailId(user.getEmailId());
            if(existingUserOptional.isEmpty()){
                throw new RuntimeException("User does not exist in the system, create a user profile first");
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
    public void deleteUser(Long userId) {
        try {
            userRepository.deleteById(userId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user");
        }
    }

    @Override
    public UserDTO updateUserCommunities(Long userId, List<Long> communityIds) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("No user found with given id"));

        List<Community> communities = communityRepository.findAllById(communityIds);
        if (communities.size() != communityIds.size()) {
            throw new RuntimeException("One or more communities not found");
        }
        user.setCommunities(new HashSet<>(communities));
        userRepository.save(user);
        return userMapper.toDTO(user);
    }
}
