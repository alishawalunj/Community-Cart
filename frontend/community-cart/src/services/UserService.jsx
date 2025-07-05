import axios from "axios";
import serviceConfig from "../config/Config";

console.log("User service called");

/**
 * @returns User Data
 */

export const getAllUsersService = async() =>{
    return await axios({
        method: 'get',
        url: `${serviceConfig.communityHost}/users/all`
    })
}

export const getUserByIdService = async(userId) => {
    return await axios({
        method: 'get',
        url: `${serviceConfig.communityHost}/getUserById/${userId}`
    })
}

export const getUserByEmailIdService = async(emailId) => {
    return await axios({
        method: 'get',
        url: `${serviceConfig.communityHost}/getUserByEmailId/${emailId}`
    })
}

// export const loginService = async(user) => {
//     return await axios({
//         method: 'post',
//         url: `${serviceConfig.communityHost}/login`,

//     })
// }
export const loginService = async (authRequest) => {
  return await axios.post(
    `${serviceConfig.communityHost}/login`,
    authRequest,
    {
      headers: {
        'Content-Type': 'application/json'
      }
    }
  );
};

export const createUserService = async(user) => {
    return await axios({
        method: 'post',
        url: `${serviceConfig.communityHost}/createUser`,
        data: user
    })
}

export const updateUserService = async(user) => {
    return await axios({
        method: 'put',
        url: `${serviceConfig.communityHost}/updateUser`,
        data: user
    })
}

export const deleteUserService = async(userId) => {
    return await axios({
        method: 'delete',
        url: `${serviceConfig.communityHost}/deleteUser/${userId}`
    })
}


export const joinCommunityService = async (userId, communityId) => {
  return await axios({
    method: "post",
    url: `${serviceConfig.communityHost}/users/join`,
    params: { userId, communityId },
  });
};


export const leaveCommunityService = async (userId, communityId) => {
  return await axios({
    method: "post",
    url: `${serviceConfig.communityHost}/users/leave`,
    params: { userId, communityId },
  });
};


export const getUserCommunitiesService = async (userId) => {
  return await axios({
    method: "get",
    url: `${serviceConfig.communityHost}/users/${userId}/communities`,
  });
};