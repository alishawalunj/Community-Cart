package com.nzefler.community_service.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@ToString
@Entity
@NoArgsConstructor
public class UserResponseDTO {
    private Long userId;
    private String firstName;
    private String lastName;
    private String emailId;
    private String password;
    private List<CommunityResponseDTO> communities;
    private List<CommunityResponseDTO> communitiesOwned;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<CommunityResponseDTO> getCommunities() {
        return communities;
    }

    public void setCommunities(List<CommunityResponseDTO> communities) {
        this.communities = communities;
    }

    public List<CommunityResponseDTO> getCommunitiesOwned() {
        return communitiesOwned;
    }

    public void setCommunitiesOwned(List<CommunityResponseDTO> communitiesOwned) {
        this.communitiesOwned = communitiesOwned;
    }
}
