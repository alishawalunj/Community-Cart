package com.nzefler.community_service.controller;

import com.nzefler.community_service.dto.UserDTO;
import com.nzefler.community_service.model.User;
import com.nzefler.community_service.service.UserServiceImpl;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @QueryMapping
    public List<UserDTO> getAllUsers(){
        return userService.findAllUsers();
    }

    @QueryMapping
    public UserDTO getUserById(@Argument Long userId){
        return userService.findUserById(userId);
    }

    @QueryMapping
    public UserDTO getUserByEmailId(@Argument String emailId){
        return userService.findUserByEmailId(emailId);
    }

    @QueryMapping
    public List<UserDTO> getUsersByCommunityId(@Argument Long communityId){
        return userService.findAllUsersByCommunityId(communityId);
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

    @MutationMapping
    public UserDTO updateUserCommunities(@Argument Long userId, @Argument List<Long> communityIds){
        return userService.updateUserCommunities(userId, communityIds);
    }
}
