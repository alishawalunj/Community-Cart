package com.nzefler.community.mapper;

import com.nzefler.community.dto.CommunityRequestDTO;
import com.nzefler.community.dto.CommunityResponseDTO;
import com.nzefler.community.dto.UserRefDTO;
import com.nzefler.community.model.Community;
import com.nzefler.community.model.UserRef;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
public class CommunityMapper {

    public CommunityResponseDTO toResponseDTO(Community community){
        CommunityResponseDTO dto = new CommunityResponseDTO();
        dto.setCommunityId(community.getCommunityId());
        dto.setName(community.getName());
        dto.setDescription(community.getDescription());
        dto.setCreatedOn(community.getCreatedOn());
        dto.setImage(community.getImage());
        dto.setOwnerId(community.getOwner().getUserId());

        Set<UserRefDTO> membersDTO = new HashSet<>();
        for(UserRef user: community.getMembers()){
            membersDTO.add(new UserRefDTO(user.getUserId(), user.getDisplayName(), user.getImage()));

        }
        dto.setMembers(membersDTO);
        return dto;
    }

    public Community toEntity(CommunityRequestDTO request, UserRef owner){
        Community community = new Community();
        community.setName(request.getName());
        community.setDescription(request.getDescription());
        community.setImage(request.getImage());
        community.setOwner(owner);
        community.setCreatedOn(LocalDate.now());
        community.getMembers().add(owner);
        return community;
    }

}
