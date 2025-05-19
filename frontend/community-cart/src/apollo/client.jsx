import { ApolloClient, InMemoryCache, HttpLink } from "@apollo/client";
import SERVICE_URLS from "../config/serviceUrls";

const craeteApolloClient = (serviceName) =>{
    const usi = SERVICE_URLS[serviceName];
    if(!uri) throw new Error(`Service ${serviceName} not found`);
    return new ApolloClient({
        link: new HttpLink({ uri}),
        cache: new InMemoryCache(),

    });;


};

export default craeteApolloClient;