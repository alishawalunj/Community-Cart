import axiosInstance from "../config/axiosInstance";

console.log("Community service called");

const COMMUNITY_BASE_URL ="http://localhost:8081/communities";

export const getAllCommunities = async() =>{
    return await axiosInstance({
        method: 'get',
        url: `${COMMUNITY_BASE_URL}/community-service/community/all`
    })
}

export const getCommunityById = async(communityId) => {
    return await axiosInstance({
        method: 'get',
        url: `${COMMUNITY_BASE_URL}/community-service/community/${communityId}`
    })
}

export const getCommunityByName = async(communityName) => {
    return await axiosInstance({
        method: 'get',
        url: `${COMMUNITY_BASE_URL}/community-service/community/${communityName}`
    })
}

export const createCommunity = async(community) => {
    return await axiosInstance({
        method: 'post',
        url: `${COMMUNITY_BASE_URL}/community-service/community`,
        data: community
    })
}

export const updateCommunity = async(community) => {
    return await axiosInstance({
        method: 'put',
        url: `${COMMUNITY_BASE_URL}/community-service/community`,
        data: community
    })
}

export const deleteCommunity = async(communityId) => {
    return await axiosInstance({
        method: 'delete',
        url: `${COMMUNITY_BASE_URL}/community-service/community/${communityId}`
    })
}