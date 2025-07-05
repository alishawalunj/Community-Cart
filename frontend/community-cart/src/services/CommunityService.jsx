import axios from "axios";
import serviceConfig from "./Config";

console.log("Community service called");

/**
 * 
 * @returns Coomunity Data
 */

export const getAllCommunitiesService = async() =>{
    return await axios({
        method: 'get',
        url: `${serviceConfig.communityHost}/community/all`
    })
}

export const getCommunityByIdService = async(communityId) => {
    return await axios({
        method: 'get',
        url: `${serviceConfig.communityHost}/getCommunityById/${communityId}`
    })
}

export const getCommunityByNameService = async(communityName) => {
    return await axios({
        method: 'get',
        url: `${serviceConfig.communityHost}/getCommunityByName/${communityName}`
    })
}

export const createCommunityService = async(community) => {
    return await axios({
        method: 'post',
        url: `${serviceConfig.communityHost}/createCommunity`,
        data: community
    })
}

export const updateCommunityService = async(community) => {
    return await axios({
        method: 'put',
        url: `${serviceConfig.communityHost}/updateCommunity`,
        data: community
    })
}

export const deleteCommunityService = async(communityId) => {
    return await axios({
        method: 'delete',
        url: `${serviceConfig.communityHost}/deleteCommunity/${communityId}`
    })
}

export const addUsersToCommunityService = async(communityId, userId) => {
    return await axios({
        method:'put',
        url:`${serviceConfig.communityHost}/addUsersToCommunitye/communityId/${communityId}/userId/${userId}`,
        data: userId 
    })
}

export const removeUsersFromCommunityService = async(communityId, userId) => {
    return await axios({
        method:'put',
        url:`${serviceConfig.communityHost}/removeUsersFromCommunity/communityId/${communityId}/userId/${userId}`,
        data: userId 
    })
}

export const getAllCommunityUsersService = async(communityId) => {
    return await axios({
        method: 'get',
        url: `${serviceConfig.communityHost}/getAllCommunityUsers/${communityId}`,
        data: communityId
    })
}