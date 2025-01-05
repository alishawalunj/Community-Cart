package com.nzefler.user_management.repository;

import com.nzefler.user_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findUserByUserName(String userNme);
}
