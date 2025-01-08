package com.nzefler.user_management.resolver;

import com.nzefler.user_management.model.User;
import com.nzefler.user_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
@Controller
public class UserResolver {

    @Autowired
    private UserService userService;

    @QueryMapping
    public List<User> getAllUsers(){
        System.out.println("in user query method for getting all user");
        return userService.getAllUsers();
    }
    @QueryMapping
    public User getUserById(@Argument Long id){
        return userService.getUserById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @MutationMapping
    public User createUser(@Argument String userName, @Argument String firstName, @Argument String lastName, @Argument String address, @Argument String email, @Argument String password){
        System.out.println("in user mutation method for create user");
        User newUser = new User();
        newUser.setUserName(userName);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setAddress(address);
        newUser.setEmail(email);
        newUser.setPassword(password);
        return userService.createUser(newUser);
    }
    @MutationMapping
    public User updateUser(@Argument String userName, @Argument String firstName, @Argument String lastName, @Argument String address, @Argument String email, @Argument String password){
        User existingUser = new User();
        existingUser.setUserName(userName);
        existingUser.setFirstName(firstName);
        existingUser.setLastName(lastName);
        existingUser.setAddress(address);
        existingUser.setEmail(email);
        existingUser.setPassword(password);
        return userService.updateUser(existingUser);
    }
    @MutationMapping
    public String deleteUser(@Argument Long id){
        userService.deleteUser(id);
        return "User deleted successfully!!";
    }
}
