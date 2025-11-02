package com.nzefler.community.service;

import com.nzefler.community.dto.CommunityRequestDTO;
import com.nzefler.community.dto.CommunityResponseDTO;
import com.nzefler.community.dto.CommunityUserResponseDTO;
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

    private User getOwner(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.USER_NOT_FOUND));
    }

    @Override
    public List<CommunityResponseDTO> findAllCommunities() {
        List<Community> communities = communityRepository.findAll();
        return communities.stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CommunityUserResponseDTO findCommunityById(Long communityId) {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.COMMUNITY_NOT_FOUND));
        return mapper.toUserResponseDTO(community);
    }

    @Override
    @Transactional
    public CommunityResponseDTO saveCommunity(CommunityRequestDTO community) {
        if (communityRepository.findByName(community.getName()).isPresent()) {
            throw new EntityAlreadyExistsException(ErrorMessages.COMMUNITY_ALREADY_EXITS);
        }
        User owner = getOwner(community.getOwner());
        Community newCommunity = mapper.toEntity(community, owner);
        communityRepository.save(newCommunity);
        return mapper.toResponseDTO(newCommunity);
    }

    @Override
    @Transactional
    public CommunityResponseDTO updateCommunity(CommunityRequestDTO community) {
        Community existingCommunity = communityRepository.findByName(community.getName())
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.COMMUNITY_NOT_FOUND));
        existingCommunity.setDescription(community.getDescription());
        existingCommunity.setName(community.getName());
        existingCommunity.setOwner(getOwner(community.getOwner()));
        existingCommunity.setImage(community.getImage());
        communityRepository.save(existingCommunity);
        return mapper.toResponseDTO(existingCommunity);
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
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.COMMUNITY_NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.USER_NOT_FOUND));
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
    public List<CommunityResponseDTO> findAllUserOwnedCommunities(Long userId) {
        User user = getOwner(userId);
        List<Community> communities = communityRepository.findByOwner(user);

        return communities.stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CommunityResponseDTO findCommunityByName(String name) {
        Community community = communityRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.COMMUNITY_NOT_FOUND));
        return mapper.toResponseDTO(community);
    }

    public void updateImageUrl(Long communityId, String imageUrl) {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new RuntimeException("Community not found with id: " + communityId));
        community.setImage(imageUrl);
        communityRepository.save(community);
    }
}
