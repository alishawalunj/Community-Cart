import axiosInstance from "./axiosInstance";
import axios from "axios";
import serviceConfig from "../config/Config";

console.log("Community service called");

/**
 * 
 * @returns Coomunity Data
 */

export const getAllCommunitiesService = async() =>{
    return await axiosInstance({
        method: 'get',
        url: `${serviceConfig.communityHost}/community/all`
    })
}

export const getCommunityByIdService = async(communityId) => {
    return await axiosInstance({
        method: 'get',
        url: `${serviceConfig.communityHost}/getCommunityById/${communityId}`
    })
}

export const getCommunityByNameService = async(communityName) => {
    return await axiosInstance({
        method: 'get',
        url: `${serviceConfig.communityHost}/getCommunityByName/${communityName}`
    })
}

export const createCommunityService = async(community) => {
    return await axiosInstance({
        method: 'post',
        url: `${serviceConfig.communityHost}/createCommunity`,
        data: community
    })
}

export const updateCommunityService = async(community) => {
    return await axiosInstance({
        method: 'put',
        url: `${serviceConfig.communityHost}/updateCommunity`,
        data: community
    })
}

export const deleteCommunityService = async(communityId) => {
    return await axiosInstance({
        method: 'delete',
        url: `${serviceConfig.communityHost}/deleteCommunity/${communityId}`
    })
}

export const addUsersToCommunityService = async(communityId, userId) => {
    return await axiosInstance({
        method:'put',
        url:`${serviceConfig.communityHost}/addUsersToCommunitye/communityId/${communityId}/userId/${userId}`,
        data: userId 
    })
}

export const removeUsersFromCommunityService = async(communityId, userId) => {
    return await axiosInstance({
        method:'put',
        url:`${serviceConfig.communityHost}/removeUsersFromCommunity/communityId/${communityId}/userId/${userId}`,
        data: userId 
    })
}

export const getAllCommunityUsersService = async(communityId) => {
    return await axiosInstance({
        method: 'get',
        url: `${serviceConfig.communityHost}/getAllCommunityUsers/${communityId}`,
        data: communityId
    })
}