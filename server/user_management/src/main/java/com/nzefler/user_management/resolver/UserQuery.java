package com.nzefler.user_management.resolver;


import com.nzefler.user_management.model.User;
import com.nzefler.user_management.service.UserService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class UserQuery implements GraphQLQueryResolver {

    @Autowired
    private UserService userService;

    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    public User getUserById(Long id){
        return userService.getUserById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
