package com.nzefler.community.repository;

import com.nzefler.community.model.Community;
import com.nzefler.community.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    @Query("SELECT c FROM Community c WHERE LOWER(c.name) = LOWER(:name)")
    Optional<Community> findByName(@Param("name") String name);
    List<Community> findByOwner(User owner);
}
