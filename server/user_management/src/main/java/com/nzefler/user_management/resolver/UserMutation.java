package com.nzefler.user_management.resolver;

import com.nzefler.user_management.model.User;
import com.nzefler.user_management.service.UserService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMutation implements GraphQLMutationResolver {

    @Autowired
    private UserService userService;

    public User createUser(String userName, String email, String password){
        User newUser = new User();
        newUser.setUserName(userName);
        newUser.setEmail(email);
        newUser.setPassword(password);
        return userService.createUser(newUser);
    }

    public User updateUser(String userName, String email, String password){
        User existingUser = new User();
        existingUser.setUserName(userName);
        existingUser.setEmail(email);
        existingUser.setPassword(password);
        return userService.updateUser(existingUser);
    }

    public String deleteUser(Long id){
        userService.deleteUser(id);
        return "User deleted successfully!!";
    }
}
