package com.nzefler.community.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="communities")
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "community_seq")
    @SequenceGenerator(name = "community_seq", sequenceName = "community_sequence", allocationSize = 1, initialValue = 1201)
    private Long communityId;
    @Column(unique = true, nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private UserRef owner;
    private String description;
    private LocalDate createdOn;
    private String image;
    @ManyToMany
    @JoinTable(name = "community_members",
            joinColumns = @JoinColumn(name = "community_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserRef> members = new HashSet<>();

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

    public UserRef getOwner() {
        return owner;
    }

    public void setOwner(UserRef owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<UserRef> getMembers() {
        return members;
    }

    public void setMembers(Set<UserRef> members) {
        this.members = members;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Community community)) return false;
        return Objects.equals(communityId, community.communityId) && Objects.equals(name, community.name) && Objects.equals(owner, community.owner) && Objects.equals(description, community.description) && Objects.equals(createdOn, community.createdOn) && Objects.equals(image, community.image) && Objects.equals(members, community.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(communityId, name, owner, description, createdOn, image, members);
    }
}
