import { gql } from '@apollo/client';

export const CREATE_PRODUCT = gql `
mutation createProduct($product: ProductInput!){
    createProduct(product:$product){
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

export const UPDATE_PRODUCT = gql `
mutation updateProduct($product: ProductInput!){
    updateProduct(product:$product){
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

export const DELETE_PRODUCT = gql `
mutation deleteProduct($productId: ID!){
    deleteProduct(productId:$productId){
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