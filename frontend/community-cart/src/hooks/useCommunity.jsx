import {
    getAllCommunitiesService,
    getCommunityByIdService,
    getCommunityByNameService,
    getAllCommunityUsersService,
    createCommunityService,
    updateCommunityService,
    deleteCommunityService,
    addUsersToCommunityService,
    removeUsersFromCommunityService,
  } from '../services/CommunityService';
import { useState } from 'react';

export const useCommunity = () => {
    const [refreshFlag, setRefreshFlag] = useState(false);  

    const refresh = () => {
        setRefreshFlag(!refreshFlag);
    }

    const getAllCommunities = async() => {
        try{
            const response = await getAllCommunitiesService();
            return response.data;
        }catch(error){
            console.error("Error from backend", error);
            return [];
        }
    }

    const getCommunityById = async(communityId) => {
        try{
            const response = await getCommunityByIdService(communityId);
            return response.data;
        }catch(error){
            console.error("Error from backend", error);
            return [];
        }
    }

    const getCommunityByName = async(communityName) => {
        try{
            const response = await getCommunityByNameService(communityName);
            return response.data;
        }catch(error){
            console.error("Error from backend", error);
            return [];
        }
    }


    const createCommunity = async(community) => {
        try{
            const response = await createCommunityService(community);
            return response.data;
        }catch(error){
            console.error("Error from backend", error);
            return [];
        }
    }

    const updateCommunity = async(community) => {
        try{
            const response = await updateCommunityService(community);
            return response.data;
        }catch(error){
            console.error("Error from backend", error);
            return [];
        }
    }

    const deleteCommunity = async(communityId) => {
        try{
            const response = await deleteCommunityService(communityId);
            return response.data;
        }catch(error){
            console.error("Error from backend", error);
            return [];
        }
    }

    const addUsersToCommunity = async(communityId, userId) => {
        try{
            const response = await addUsersToCommunityService(communityId, userId);
            return response.data;
        }catch(error){
            console.error("Error from backend", error);
            return [];
        }

    }

    const removeUsersFromCommunity = async(communityId, userId) => {
        try{
            const response = await removeUsersFromCommunityService(communityId, userId);
            return response.data;
        }catch(error){
            console.error("Error from backend", error);
            return [];
        }
    }
    const getCommunityUsers = async(communityId) => {
        try{
            const response = await getAllCommunityUsersService(communityId);
            console.log("Alsihaw",response.data);
            return response.data;
        }catch(error){
            console.error("Error from backend", error);
            return [];
        }
    }

    return {
        refresh,
        getAllCommunities,
        getCommunityById,
        getCommunityByName,
        getCommunityUsers,
        createCommunity,
        updateCommunity,
        deleteCommunity,
        addUsersToCommunity,
        removeUsersFromCommunity
    }
}