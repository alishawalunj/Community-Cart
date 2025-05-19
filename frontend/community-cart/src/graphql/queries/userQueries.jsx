import { gql } from  '@apollo/client';

export const GET_ALL_USERS = gql`
    query{
        getAllUSers{
            userId
            firstName
            lastName
            emailId
        }
    }`;

export const GET_USER_BY_ID = gql`
    query getUserById($userId: ID!){
        getUserById(userId:$userId){
            userId
            firstName
            lastName
            emailId
        }
    }`;

export const GET_USER_BY_EMAIL_ID = gql`
    query getUserByEmailId($emailId: String!){
        getUserByEmailId(emailId:$emailId){
            userId
            firstName
            lastName
            emailId
        }
    }`;

export const GET_USER_BY_COMMUNITY_ID = gql`
    query getUsersBYCommunityId($communityId: ID!){
        getUsersBYCommunityId(communityId:$communityId){
            userId
            firstName
            lastName
            emailId
        }
    }`;


