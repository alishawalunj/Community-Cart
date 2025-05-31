import { useQuery, useLazyQuery, useMutation  } from "@apollo/client";  
import { GET_ALL_PRODUCTS, GET_PRODUCT_BY_ID, GET_PRODUCTS_BY_COMMUNITY_ID,GET_PRODUCTS_BY_USER_ID} from "../graphql/queries/productQueries";
import { CREATE_PRODUCT, UPDATE_PRODUCT, DELETE_PRODUCT } from "../graphql/mutations/productMutations";
const useProducts = () =>{
    const { data: allProductsResponse, loading: allProductsLoading, error:allProductsError} = useQuery(GET_ALL_PRODUCTS);
    console.log("Products Response from baccccckend 1:",allProductsResponse);
    const [fetchProductById, { data: productByIdResponse, loading: productByIdLoading, error: productByIdError}] = useLazyQuery(GET_PRODUCT_BY_ID);
    const [fetchProductsByCommunityId, { data: productsByCommunityIdResponse, loading: productsByCommunityIdLoading, error:productsByCommunityIdError}] = useLazyQuery(GET_PRODUCTS_BY_COMMUNITY_ID);
    const [fetchProductsByUserId, {data:fetchProductsByUserIdResponse, loading:fetchProductsByUserIdLoading, error:fetchProductsByUserIdError}] = useLazyQuery(GET_PRODUCTS_BY_USER_ID);
    const [createProduct, { loading: creatingProduct }] = useMutation(CREATE_PRODUCT);
    const [updateProduct, { loading: updatingProduct }] = useMutation(UPDATE_PRODUCT);
    const [deleteProduct, { loading: deletingProduct }] = useMutation(DELETE_PRODUCT);
    return{
        //allProducts
        allProducts: allProductsResponse?.getAllProducts || [],
        allProductsLoading,
        allProductsError,

        //productsById
        fetchProductById,
        productsById:productByIdResponse?.productsById || null,
        productByIdLoading,
        productByIdError,

        //ProductsByCommunityId
        fetchProductsByCommunityId,
        productsByCommunityId:productsByCommunityIdResponse?.productsByCommunityId || [],
        productsByCommunityIdLoading,
        productsByCommunityIdError,

        //ProductByUserId
        fetchProductsByUserId,
        productsByUserId:fetchProductsByUserIdResponse?.productsByUserId || [],
        fetchProductsByUserIdLoading,
        fetchProductsByUserIdError,

        //createProduct
        createProduct,
        creatingProduct,

        //updateProduct
        updateProduct,
        updatingProduct,

        //deleteProduct
        deleteProduct,
        deletingProduct
    };
};

export default useProducts;