package com.nzefler.community_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.List;
@AllArgsConstructor
@ToString
@Entity
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long communityId;
    private String name;
    private String owner;
    private String description;
    private String createdOn;
    private List<User> membersList;

    public Community(){

    }

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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
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

    public List<User> getMembersList() {
        return membersList;
    }

    public void setMembersList(List<User> membersList) {
        this.membersList = membersList;
    }
}
