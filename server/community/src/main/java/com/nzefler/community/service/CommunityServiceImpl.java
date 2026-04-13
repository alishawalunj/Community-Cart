package com.nzefler.community.service;

import com.nzefler.community.dto.CommunityRequestDTO;
import com.nzefler.community.dto.CommunityResponseDTO;
import com.nzefler.community.dto.UserRefDTO;
import com.nzefler.community.exception.EntityAlreadyExistsException;
import com.nzefler.community.exception.EntityNotFoundException;
import com.nzefler.community.exception.ErrorConstants;
import com.nzefler.community.exception.UnAuthorizedException;
import com.nzefler.community.mapper.CommunityMapper;
import com.nzefler.community.model.Community;
import com.nzefler.community.model.UserRef;
import com.nzefler.community.repository.CommunityRepository;
import com.nzefler.community.repository.UserRefRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CommunityServiceImpl implements CommunityService{

    private final CommunityRepository communityRepository;
    private final UserRefRepository userRefRepository;
    private final CommunityMapper communityMapper;

    public CommunityServiceImpl(CommunityRepository communityRepository, UserRefRepository userRefRepository, CommunityMapper communityMapper) {
        this.communityRepository = communityRepository;
        this.userRefRepository = userRefRepository;
        this.communityMapper = communityMapper;
    }

    @Override
    public List<CommunityResponseDTO> findAllCommunities() {
        return communityRepository.findAll().stream().map(communityMapper::toResponseDTO).toList();
    }

    @Override
    public CommunityResponseDTO findById(Long communityId) {
        Community community = findCommunityOrThrow(communityId);
        return communityMapper.toResponseDTO(community);
    }

    @Override
    public CommunityResponseDTO create(Long ownerId, CommunityRequestDTO request) {
        if(communityRepository.findByName(request.getName()).isPresent()){
            throw new EntityAlreadyExistsException(ErrorConstants.COMMUNITY_ALREADY_EXITS);
        }
        UserRef owner = findUserRefOrThrow(ownerId);
        Community community = communityMapper.toEntity(request, owner);
        Community saved = communityRepository.save(community);
        return communityMapper.toResponseDTO(saved);
    }

    @Override
    public CommunityResponseDTO update(Long communityId, Long requestingUserId, CommunityRequestDTO request) {
        Community community = findCommunityOrThrow(communityId);
        assertIsOwner(community, requestingUserId);
        community.setName(request.getName());
        community.setDescription(request.getName());
        community.setImage(request.getImage());

        Community saved = communityRepository.save(community);
        return communityMapper.toResponseDTO(saved);
    }

    @Override
    public void delete(Long communityId, Long requestingUserId) {
        Community community = findCommunityOrThrow(communityId);
        assertIsOwner(community, requestingUserId);
        communityRepository.delete(community);
    }

    @Override
    public void addMember(Long communityId, Long userId) {
        Community community = findCommunityOrThrow(communityId);
        UserRef user = findUserRefOrThrow(userId);
        community.getMembers().add(user);
        communityRepository.save(community);
    }

    @Override
    public void removeMember(Long communityId, Long userId) {
        Community community = findCommunityOrThrow(communityId);
        community.getMembers().removeIf(m -> m.getUserId().equals(userId));
        communityRepository.save(community);
    }

    @Override
    public List<UserRefDTO> getMembers(Long communityId) {
        Community community = findCommunityOrThrow(communityId);
        return community.getMembers().stream().map(u -> new UserRefDTO(u.getUserId(), u.getDisplayName(), u.getImage())).toList();
    }

    @Override
    public List<CommunityResponseDTO> explore(Long userId) {
        return communityRepository.findByOwnerUserIdNot(userId).stream().map(communityMapper::toResponseDTO).toList();
    }

    @Override
    public List<Long> getUserCommunityIds(Long userId) {
        return communityRepository.findCommunityIdsByMemberId(userId);
    }

    private Community findCommunityOrThrow(Long id){
        return communityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorConstants.COMMUNITY_NOT_FOUND));
    }

    private UserRef findUserRefOrThrow(Long userId){
        return userRefRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(ErrorConstants.USER_NOT_FOUND));
    }

    private void assertIsOwner(Community community, Long requestingUserId){
        if(!community.getOwner().getUserId().equals(requestingUserId)){
            throw new UnAuthorizedException(ErrorConstants.NOT_OWNER);
        }
    }
}
