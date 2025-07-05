package com.nzefler.community.dto;

import com.nzefler.community.model.Community;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
public class UserCommunityResponseDTO {
    private Long userId;
    private String firstName;
    private String lastName;
    private String emailId;
    private String password;
    private Set<CommunityResponseDTO> communities = new HashSet<>();

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

    public Set<CommunityResponseDTO> getCommunities() {
        return communities;
    }

    public void setCommunities(Set<CommunityResponseDTO> communities) {
        this.communities = communities;
    }
}
