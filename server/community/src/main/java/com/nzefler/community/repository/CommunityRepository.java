package com.nzefler.community.repository;

import com.nzefler.community.model.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    @Query("SELECT c FROM Community c WHERE LOWER(c.name) = LOWER(:name)")
    Optional<Community> findByName(@Param("name") String name);

    List<Community> findByOwnerUserIdNot(Long userId);

    @Query("SELECT c\n" +
            "FROM Community c\n" +
            "JOIN c.members m\n" +
            "WHERE m.userId = :userId")
    List<Long> findCommunityIdsByMemberId(Long userId);

}
