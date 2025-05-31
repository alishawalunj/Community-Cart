import {ApolloClient, InMemoryCache, HttpLink} from "@apollo/client";
import SERVICE_URLS from "../config/serviceUrls";

const createClient = (serviceName) =>{
    const uri = SERVICE_URLS[serviceName];
    if(!uri) throw new Error(`Service ${serviceName} not found`);
    return new ApolloClient({
        link: new HttpLink({ uri}),
        cache: new InMemoryCache(),

    });;
};
const clients = {
    user : createClient("user"),
    products : createClient("products")
};
export default clients;