package com.nzefler.community.mapper;

import com.nzefler.community.dto.CommunityResponseDTO;
import com.nzefler.community.dto.UserCommunityResponseDTO;
import com.nzefler.community.dto.UserRequestDTO;
import com.nzefler.community.dto.UserResponseDTO;
import com.nzefler.community.model.Community;
import com.nzefler.community.model.User;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserMapper {

    // Removed @Autowired CommunityMapper to avoid circular dependency
    // Map communities manually here

    public UserResponseDTO toDTO(User user){
        UserResponseDTO userResponse = new UserResponseDTO();
        userResponse.setUserId(user.getUserId());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmailId(user.getEmailId());
        userResponse.setPassword(user.getPassword());
        userResponse.setImage(user.getImage());
        return userResponse;
    }

    public User toEntity(UserRequestDTO userRequestDTO){
        User toSaveUser = new User();
        toSaveUser.setEmailId(userRequestDTO.getEmailId());
        toSaveUser.setFirstName(userRequestDTO.getFirstName());
        toSaveUser.setLastName(userRequestDTO.getLastName());
        toSaveUser.setPassword(userRequestDTO.getPassword());
        toSaveUser.setImage(userRequestDTO.getImage());
        return toSaveUser;
    }

    public UserCommunityResponseDTO toEntity(User user){
        UserCommunityResponseDTO response = new UserCommunityResponseDTO();
        response.setUserId(user.getUserId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmailId(user.getEmailId());
        response.setPassword(user.getPassword());
        response.setImage(user.getImage());

        Set<CommunityResponseDTO> communities = new HashSet<>();
        for(Community community: user.getCommunities()){
            CommunityResponseDTO communityResponseDTO = new CommunityResponseDTO();
            communityResponseDTO.setCommunityId(community.getCommunityId());
            communityResponseDTO.setName(community.getName());
            communityResponseDTO.setDescription(community.getDescription());
            communityResponseDTO.setCreatedOn(community.getCreatedOn());
            communityResponseDTO.setImage(community.getImage());

            // Map owner manually to avoid injecting CommunityMapper
            if (community.getOwner() != null) {
                UserResponseDTO ownerDTO = new UserResponseDTO();
                ownerDTO.setUserId(community.getOwner().getUserId());
                ownerDTO.setFirstName(community.getOwner().getFirstName());
                ownerDTO.setLastName(community.getOwner().getLastName());
                ownerDTO.setEmailId(community.getOwner().getEmailId());
                ownerDTO.setPassword(community.getOwner().getPassword());
                ownerDTO.setImage(community.getOwner().getImage());
                communityResponseDTO.setOwner(ownerDTO);
            }

            communities.add(communityResponseDTO);
        }

        response.setCommunities(communities);
        return response;
    }
}
