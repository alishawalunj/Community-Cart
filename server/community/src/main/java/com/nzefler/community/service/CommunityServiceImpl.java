package com.nzefler.community.service;

import com.nzefler.community.dto.CommunityRequestDTO;
import com.nzefler.community.dto.CommunityResponseDTO;
import com.nzefler.community.dto.UserResponseDTO;
import com.nzefler.community.exception.EntityAlreadyExistsException;
import com.nzefler.community.exception.EntityNotFoundException;
import com.nzefler.community.exception.ErrorMessages;
import com.nzefler.community.mapper.CommunityMapper;
import com.nzefler.community.mapper.UserMapper;
import com.nzefler.community.model.Community;
import com.nzefler.community.model.User;
import com.nzefler.community.repository.CommunityRepository;
import com.nzefler.community.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;



@Service
public class CommunityServiceImpl implements CommunityService{

    private final CommunityRepository communityRepository;
    private final CommunityMapper mapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public CommunityServiceImpl(CommunityRepository communityRepository, CommunityMapper mapper, UserRepository userRepository,UserMapper userMapper) {
        this.communityRepository = communityRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<CommunityResponseDTO> findAllCommunities() {
        try{
            return communityRepository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
        }catch(Exception e){
            throw new RuntimeException(ErrorMessages.ERROR_IN_PROCESSING);
        }
    }
    @Transactional
    @Override
    public CommunityResponseDTO findCommunityById(Long communityId) {
//        try{
            return communityRepository.findByIdWithUsers(communityId).map(mapper::toDTO).orElseThrow(() -> new EntityNotFoundException(ErrorMessages.COMMUNITY_NOT_FOUND));
//        }catch(Exception e){
//            throw new RuntimeException(ErrorMessages.ERROR_IN_PROCESSING);
//        }
    }

    @Override
    @Transactional
    public CommunityResponseDTO saveCommunity(CommunityRequestDTO community) {
        if(communityRepository.findByName(community.getName()).isPresent()){
            throw new EntityAlreadyExistsException(ErrorMessages.COMMUNITY_ALREADY_EXITS);
        }
        Community newCommunity = mapper.toEntity(community);
        communityRepository.save(newCommunity);
//            return communityRepository.findByName(community.getName());
        return mapper.toDTO(newCommunity);

    }

    @Override
    @Transactional
    public CommunityResponseDTO updateCommunity(CommunityRequestDTO community) {
        Optional<Community> optionalCommunity = communityRepository.findByName(community.getName());
        if(optionalCommunity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessages.COMMUNITY_NOT_FOUND);
        }
        Community existingCommunity = optionalCommunity.get();
        existingCommunity.setDescription(community.getDescription());
        existingCommunity.setName(community.getName());
        existingCommunity.setOwner(community.getOwner());
        communityRepository.save(existingCommunity);
        return mapper.toDTO(existingCommunity);
    }

    @Override
    @Transactional
    public void deleteCommunity(Long communityId) {
        if (!communityRepository.existsById(communityId)) {
            throw new EntityNotFoundException(ErrorMessages.COMMUNITY_NOT_FOUND);
        }
        communityRepository.deleteById(communityId);
    }

    @Override
    @Transactional
    public Boolean addUsersToCommunity(Long communityId, Long userId) {
        Community community = communityRepository.findById(communityId).orElseThrow(() -> new EntityNotFoundException(ErrorMessages.COMMUNITY_NOT_FOUND));
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(ErrorMessages.USER_NOT_FOUND));
        community.getUsers().add(user);
        user.getCommunities().add(community);
        userRepository.save(user);
        return true;
    }

    @Override
    @Transactional
    public Boolean removeUsersFromCommunity(Long communityId, Long userId) {
        Community community = communityRepository.findById(communityId).orElseThrow(() -> new EntityNotFoundException(ErrorMessages.COMMUNITY_NOT_FOUND));
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(ErrorMessages.USER_NOT_FOUND));
        community.getUsers().remove(user);
        user.getCommunities().remove(community);
        userRepository.save(user);
        return true;
    }

    @Override
    public List<UserResponseDTO> findAllCommunityUsers(Long communityId) {
        Community community = communityRepository.findById(communityId).orElseThrow(() -> new EntityNotFoundException(ErrorMessages.COMMUNITY_NOT_FOUND));
        Set<User> users = community.getUsers();
        return users.stream().map(userMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public CommunityResponseDTO findCommunityByName(String name) {
        return communityRepository.findByName(name).map(mapper::toDTO).orElseThrow(() -> new EntityNotFoundException(ErrorMessages.COMMUNITY_NOT_FOUND));
    }
}
