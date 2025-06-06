package com.nzefler.community_service.repository;

import com.nzefler.community_service.model.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    @Query("SELECT c FROM Community c WHERE LOWER(c.name) = LOWER(:name)")
    Optional<Community> findByName(@Param("name") String name);

    @Query("SELECT c FROM Community c LEFT JOIN FETCH c.users WHERE c.communityId = :communityId")
    Optional<Community> findByIdWithUsers(@Param("communityId") Long communityId);
}
