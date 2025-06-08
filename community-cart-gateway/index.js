require('dotenv').config();
const { ApolloServer } = require('apollo-server');
const { ApolloGateway } = require('@apollo/gateway');

const gateway = new ApolloGateway({
  serviceList: [
    { name: 'userCommunity', url: process.env.USER_COMMUNITY_URL },
    { name: 'product', url: process.env.PRODUCT_URL },
    { name: 'cart', url: process.env.CART_URL },
  ],
});

const server = new ApolloServer({
  gateway,
  subscriptions: false,
  introspection: true,
  playground: true,
});

server.listen({ port: 4000 }).then(({ url }) => {
  console.log(`Gateway ready at ${url}`);
});
