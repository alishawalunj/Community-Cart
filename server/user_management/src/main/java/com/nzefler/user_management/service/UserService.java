package com.nzefler.user_management.service;

import com.nzefler.user_management.model.User;

import java.util.List;
import java.util.Optional;


public interface UserService {

    public List<User> getAllUsers();
    public Optional<User> getUserById(Long id);
    public User createUser(User user);
    public User updateUser(User user);
    public void deleteUser(Long id);

}
