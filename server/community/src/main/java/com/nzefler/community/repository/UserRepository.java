package com.nzefler.community.repository;


import com.nzefler.community.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u from User u WHERE LOWER(u.emailId) = LOWER(:emailId)")
    Optional<User> findByEmailId(@Param("emailId") String emailId);

    List<User> findByCommunities_CommunityId(Long communityId);
}
