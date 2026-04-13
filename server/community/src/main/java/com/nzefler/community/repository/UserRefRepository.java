package com.nzefler.community.repository;

import com.nzefler.community.model.UserRef;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRefRepository extends JpaRepository<UserRef, Long> {
}
