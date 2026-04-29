const serviceConfig = {
  host: import.meta.env.VITE_API_APP_HOST,
  userHost: "http://localhost:8081/user-service",
  communityHost: "http://localhost:8082/community-service",
  productHost: "http://localhost:8083/product-service",
  cartHost: "http://localhost:8084/cart-service",
  orderHost: "http://localhost:8085/order-service",
  notificationHost: "http://localhost:8086/notification-service",
};

export default serviceConfig;