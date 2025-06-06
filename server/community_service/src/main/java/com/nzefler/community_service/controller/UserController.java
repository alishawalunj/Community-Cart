package com.nzefler.community_service.controller;

import com.nzefler.community_service.dto.CommunityResponseDTO;
import com.nzefler.community_service.dto.UserRequestDTO;
import com.nzefler.community_service.dto.UserResponseDTO;
import com.nzefler.community_service.model.Community;
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
    public List<UserResponseDTO> getAllUsers(){
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
    public UserResponseDTO createUser(@Argument UserRequestDTO userRequestDTO){
        return userService.saveUser(userRequestDTO);
    }

    @MutationMapping
    public UserResponseDTO updateUser(@Argument UserRequestDTO userRequestDTO){
        return userService.updateUser(userRequestDTO);
    }

    @MutationMapping
    public String deleteUser(@Argument Long userId){
        return userService.deleteUser(userId);
    }

    @MutationMapping
    public Boolean joinCommunity(@Argument Long userId, @Argument Long communityId){
        return userService.joinCommunity(userId,communityId);
    }

    @MutationMapping
    public Boolean leaveCommunity(@Argument Long userId, @Argument Long communityId){
        return userService.leaveCommunity(userId, communityId);
    }

    @QueryMapping
    public List<CommunityResponseDTO> getUserCommunities(@Argument Long userId){
        return userService.findAllUserCommunities(userId);
    }
}
