package com.nzefler.community_service.service;

import com.nzefler.community_service.dto.UserResponseDTO;
import com.nzefler.community_service.mapper.UserMapper;
import com.nzefler.community_service.model.User;
import com.nzefler.community_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserResponseDTO findUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new RuntimeException("No user Found with given user id");
        }
        return user.map(userMapper::toDTO).orElseThrow(() -> new RuntimeException("Error fetching user"));
    }

    @Override
    public UserResponseDTO findUserByEmailId(String emailId) {
        Optional<User> existingUser = userRepository.findByEmailId(emailId);
        if(existingUser.isEmpty()){
            throw new RuntimeException("User is not the existing one");
        }
        return existingUser.map(userMapper::toDTO).orElseThrow(() -> new RuntimeException("Error fetching user"));
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
}
