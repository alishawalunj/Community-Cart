package com.nzefler.user.controller;

import com.nzefler.user.dto.UserRequestDTO;
import com.nzefler.user.dto.UserResponseDTO;
import com.nzefler.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> fidAll(){
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @PostMapping("/save")
    public ResponseEntity<UserResponseDTO> saveUser(@RequestBody UserRequestDTO request){
        UserResponseDTO responseDTO = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<UserResponseDTO> updateUser(@RequestParam Long userId, @RequestBody UserRequestDTO request){
        UserResponseDTO responseDTO = userService.update(userId, request);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/id/{userId}")
    public ResponseEntity<UserResponseDTO> fetchUserById(@PathVariable Long userId){
        UserResponseDTO responseDTO = userService.findById(userId);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDTO> fetchUserByEmail(@PathVariable String email){
        UserResponseDTO responseDTO = userService.findByEmail(email);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId){
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }

}
