import { gql } from  '@apollo/client';

export const GET_ALL_COMMUNITIES = gql`
query{
    getAllCommunities{
        communityId
        name
        owner
        description
        createdOn
        users{
            userId
            firstName
            lastName
            emailId
        }
    }
}`;

export const GET_COMMUNITY_BY_ID = gql`
    query getCommunityById($communityId: ID!){
        getCommunityById(communityId:$communityId){
            communityId
            name
            owner
            description
            createdOn
            users{
                userId
                firstName
                lastName
                emailId
            }
        }
    }`;

export const GET_COMMUNITY_BY_NAME = gql`
query getCommunityByName($name: String!){
    getCommunityByName(name:$name){
        communityId
        name
        owner
        description
        createdOn
        users{
            userId
            firstName
            lastName
            emailId
        }
    }
}`;