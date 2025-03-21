package com.nzefler.community_service.controller;

import com.nzefler.community_service.dto.UserResponseDTO;
import com.nzefler.community_service.model.User;
import com.nzefler.community_service.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @QueryMapping
    public List<User> getAllUsers(){
        return userService.findAllUsers();
    }

    @QueryMapping
    public UserResponseDTO getUserById(@Argument Long userId){
        return userService.findUserById(userId);
    }

    @QueryMapping
    public UserResponseDTO getUserByEmailId(@Argument String emailId){
        return userService.findUserByEmailId(emailId);
    }

    @MutationMapping
    public String createUser(@Argument User user){
        return userService.saveUser(user);
    }

    @MutationMapping
    public User updateUser(@Argument User user){
        return userService.updateUser(user);
    }

    @MutationMapping
    public void deleteUser(@Argument Long userId){
        userService.deleteUser(userId);
    }

}
