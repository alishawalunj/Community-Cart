import { gql } from  '@apollo/client';

export const GET_ALL_PRODUCTS = gql `
query{
    getAllProducts{
        productId
        userId
        communityId
        name
        description
        tag
        color
        size
        price
        count
    }
}`

export const GET_PRODUCT_BY_ID = gql `
query getProductById($productId: ID!){
    getProductById(productId:$productId){
        productId
        userId
        communityId
        name
        description
        tag
        color
        size
        price
        count
    }
}`

export const GET_PRODUCTS_BY_COMMUNITY_ID = gql `
query getProductsByCommunityId($communityId: ID!){
    getProductsByCommunityId(communityId:$communityId){
        productId
        userId
        communityId
        name
        description
        tag
        color
        size
        price
        count
    }
}`

export const GET_PRODUCTS_BY_USER_ID = gql`
query getProductsByUserId($userId: ID!){
    getProductsByUserId(userId:$userId){
        productId
        userId
        communityId
        name
        description
        tag
        color
        size
        price
        count
    }}`