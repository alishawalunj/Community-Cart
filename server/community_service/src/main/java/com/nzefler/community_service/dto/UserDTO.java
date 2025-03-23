package com.nzefler.community_service.dto;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userId;
    private String firstName;
    private String lastName;
    private String emailId;
    private String password;
//    private Set<Long> communityIds;


//    public UserDTO(User user){
//        this.userId = user.getUserId();
//        this.firstName = user.getFirstName();
//        this.lastName = user.getLastName();
//        this.emailId = user.getEmailId();
//        this.password = user.getPassword();
//        this.communityIds = user.getCommunities().stream().map(Community::getCommunityId).collect(Collectors.toSet());
//    }

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

//    public Set<Long> getCommunityIds() {
//        return communityIds;
//    }
//
//    public void setCommunityIds(Set<Long> communityIds) {
//        this.communityIds = communityIds;
//    }
}
