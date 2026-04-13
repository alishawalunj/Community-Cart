package com.nzefler.user.service;

import com.nzefler.user.dto.UserRequestDTO;
import com.nzefler.user.dto.UserResponseDTO;
import com.nzefler.user.enums.ErrorConstants;
import com.nzefler.user.exception.EntityAlreadyExistsException;
import com.nzefler.user.exception.EntityNotFoundException;
import com.nzefler.user.mapper.UserMapper;
import com.nzefler.user.model.User;
import com.nzefler.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper mapper,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDTO register(UserRequestDTO request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EntityAlreadyExistsException(ErrorConstants.USER_ALREADY_EXITS);
        }
        User user = mapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User savedUser = userRepository.save(user);
        return mapper.toResponseDTO(savedUser);
    }

    @Override
    public UserResponseDTO findById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(ErrorConstants.USER_NOT_FOUND));
        return mapper.toResponseDTO(user);
    }

    @Override
    public UserResponseDTO update(Long userId, UserRequestDTO request) {

        User existingUser = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(ErrorConstants.USER_NOT_FOUND));
        if (request.getEmail() != null &&
                userRepository.findByEmail(request.getEmail())
                        .filter(user -> !user.getUserId().equals(userId))
                        .isPresent()) {
            throw new EntityAlreadyExistsException(ErrorConstants.USER_ALREADY_EXITS);
        }
        existingUser.setFirstName(request.getFirstName());
        existingUser.setLastName(request.getLastName());
        existingUser.setEmail(request.getEmail());
        if (request.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        existingUser.setImageUrl(request.getImageUrl());
        existingUser.setRole(request.getRole());
        User updatedUser = userRepository.save(existingUser);
        return mapper.toResponseDTO(updatedUser);
    }

    @Override
    public void delete(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException(ErrorConstants.USER_NOT_FOUND);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public UserResponseDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(ErrorConstants.USER_NOT_FOUND));
        return mapper.toResponseDTO(user);

    }
}