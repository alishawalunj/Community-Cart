package com.nzefler.user_management.service;

import com.nzefler.user_management.model.User;
import com.nzefler.user_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User createUser(User user) {
        if(userRepository.findUserByUserName(user.getUserName()) == null){
            throw new RuntimeException("User already Exists");
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        User existingUser = userRepository.findUserByUserName(user.getUserName());
        if(existingUser == null){
            throw new RuntimeException("User does not exist, please create an account first!!");
        }else{
            existingUser.setUserName(user.getUserName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
        }
        return existingUser;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
