package com.nzefler.community_service.repository;

import com.nzefler.community_service.model.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long> {
}
