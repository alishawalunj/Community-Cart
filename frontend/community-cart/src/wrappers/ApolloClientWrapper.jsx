import React from 'react';
import { ApolloProvider } from '@apollo/client';
// import client from '../apollo/client';
import clients from '../apollo/client';

const ApolloClientWrapper = (WrappedComponent, serviceName) => {
    const client = clients[serviceName];
    if(!client){
        throw new Error("No client found");
    }
    return function ApolloClientWrapper(props) {
        return (
            <ApolloProvider client={client}>
                <WrappedComponent {...props} />
            </ApolloProvider>
        );
    };
};

export default ApolloClientWrapper;