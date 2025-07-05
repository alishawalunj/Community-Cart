import {
    getAllUsersService,
    getUserByIdService,
    getUserByEmailIdService,
    getUserCommunitiesService,
    createUserService,
    updateUserService,
    deleteUserService,
    joinCommunityService,
    leaveCommunityService,
    loginService
  } from '../services/UserService';
import { useState } from 'react';

export const useUser = () => {

    const [refreshFlag, setRefreshFlag] = useState(false);

    const refresh = () => {
        setRefreshFlag(!refreshFlag);
    }

    const getAllUsers = async() => {
        try{
            const response = await getAllUsersService();
            return response.data;
        }catch(error){
            console.error("Error from backend", error);
            return [];
        }
    }

    const getUserById = async(userId) => {
        try{
            const response = await getUserByIdService(userId);
            return response.data;
        }catch(error){
            console.error("Error from backend", error);
            return [];
        }
    }

    const getUserByEmailId = async(emailId) => {
        try{
            const response = await getUserByEmailIdService(emailId);
            return response.data;
        }catch(error){
            console.error("Error from backend", error);
            return [];
        }
    }

    const login = async(user) => {
        try{
            const response = await loginService(user);
            return response.data;
        }catch(error){
            console.error("Error while logging in : ", error);
            return [];
        }
    }

    const getUserCommunities = async(userId) => {
        try{
            const response = await getUserCommunitiesService(userId);
            return response.data;
        }catch(error){
            console.error("Error from backend", error);
            return [];
        }
    }

    const createUser = async(user) => {
        try{
            const response = await createUserService(user);
            return response.data;
        }catch(error){
            console.error("Error from backend", error);
            return [];
        }
    }

    const updateUser = async(user) => {
        try{
            const response = await updateUserService(user);
            return response.data;
        }catch(error){
            console.error("Error from backend", error);
            return [];
        }
    }

    const deleteUser = async(userId) => {
        try{
            const response = await deleteUserService(userId);
            return response.data;
        }catch(error){
            console.error("Error from backend", error);
            return [];
        }
    }

    const joinCommunity = async(communityId, userId) => {
        try{
            const response = await joinCommunityService(communityId, userId);
            return response.data;
        }catch(error){
            console.error("Error from backend", error);
            return [];
        }
    }

    const leaveCommunity= async(communityId, userId) => {
        try{
            const response = await leaveCommunityService(communityId, userId);
            return response.data;
        }catch(error){
            console.error("Error from backend", error);
            return [];
        }
    }

    


    return {
        getAllUsers,
        getUserById,
        getUserByEmailId,
        getUserCommunities,
        createUser,
        updateUser,
        deleteUser,
        joinCommunity,
        leaveCommunity,
        login
    }
}