package com.nzefler.community.dto;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
public class CommunityUserResponseDTO {
    private Long communityId;
    private String name;
    private UserResponseDTO owner;
    private String description;
    private String createdOn;
    private String image;
    private Set<UserResponseDTO> users = new HashSet<>();

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserResponseDTO getOwner() {
        return owner;
    }

    public void setOwner(UserResponseDTO owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<UserResponseDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserResponseDTO> users) {
        this.users = users;
    }
}
