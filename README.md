# 🛒 CommunityCart

> A community-scoped marketplace platform where users create and join communities to buy and sell products exclusively within those communities. Products listed in one community are never visible outside it.

---

## 🚀 Tech Stack

### Frontend
![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)
![TailwindCSS](https://img.shields.io/badge/Tailwind_CSS-38B2AC?style=for-the-badge&logo=tailwind-css&logoColor=white)
![Redux](https://img.shields.io/badge/Redux-593D88?style=for-the-badge&logo=redux&logoColor=white)
![Axios](https://img.shields.io/badge/Axios-5A29E4?style=for-the-badge&logo=axios&logoColor=white)

### Backend
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)

### Data & Messaging
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)
![Apache Kafka](https://img.shields.io/badge/Apache_Kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white)

### Auth & Payments
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)
![Stripe](https://img.shields.io/badge/Stripe-635BFF?style=for-the-badge&logo=stripe&logoColor=white)

### DevOps
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white)

---

## 🏗️ Architecture

### 1. High-Level Architecture

```
┌─────────────────────────────────────────────────────────────────────┐
│                          CLIENT LAYER                               │
│                   React SPA (Port 3000)                             │
└─────────────────────────┬───────────────────────────────────────────┘
                          │  HTTPS
                          ▼
┌─────────────────────────────────────────────────────────────────────┐
│                     API GATEWAY  :8080                              │
│          JWT Validation · Rate Limiting · Request Routing           │
│                   Sets X-User-Id header downstream                  │
└──────┬──────────┬──────────┬──────────┬──────────┬─────────────────┘
       │          │          │          │          │
       ▼          ▼          ▼          ▼          ▼
   Auth Svc  Community   Product     Cart       Order Svc
    :8081     Svc :8082  Svc :8083  Svc :8084    :8085
       │          │          │          │          │
       ▼          ▼          ▼          ▼          ▼
   auth_db  community_db product_db  cart_db   order_db
   (MySQL)    (MySQL)     (MySQL)    (MySQL)    (MySQL)

                               │  Kafka Events
                               ▼
                    ┌──────────────────────┐
                    │  Notification Svc    │
                    │        :8086         │
                    │   (Kafka Consumer)   │
                    └──────────────────────┘

External Integrations:
  ├── Stripe         →  Payment processing  (Order Service)
  ├── SendGrid/SMTP  →  Email delivery      (Notification Service)
  └── Redis          →  Token cache · Membership cache  (shared)
```

---

### 2. Microservices Architecture

```
                ┌────────────────────────────────────┐
                │         SERVICE REGISTRY           │
                │      (Eureka  :8761)               │
                └────────────────────────────────────┘

┌──────────────┐  REST  ┌───────────────┐  REST  ┌───────────────┐
│ Auth Service │◄──────►│  API Gateway  │◄──────►│  React Client │
│              │        │               │        └───────────────┘
│ - Register   │        │ - JWT verify  │
│ - Login      │        │ - Routing     │
│ - Refresh    │        │ - Rate limit  │
└──────┬───────┘        └───────┬───────┘
       │ JWT issued             │
       │               ┌────────┴──────────────────────────────┐
       │               │         │           │                 │
       │               ▼         ▼           ▼                 ▼
       │        ┌──────────┐ ┌────────┐ ┌────────┐  ┌──────────────┐
       │        │Community │ │Product │ │  Cart  │  │    Order     │
       │        │ Service  │ │Service │ │Service │  │   Service    │
       │        └────┬─────┘ └───┬────┘ └───┬────┘  └──────┬───────┘
       │             │       REST│       REST│              │
       │             └───────────┘           │      ┌───────┴──────┐
       │           membership IDs            │      │   Payment    │
       │                                     │      │  (embedded)  │
       │                                     │      └───────┬──────┘
       │                          ┌──────────┘              │
       │                          │        Kafka            │
       │                          └──────────────────┐      │
       │                                             ▼      ▼
       │                                   ┌──────────────────────┐
       │         user.registered event     │  Notification Svc    │
       └───────────────────────────────────►  Email · Push        │
                                           └──────────────────────┘

Communication:
  REST (synchronous)
    Auth ↔ Gateway
    Product → Community  (membership check on every product query)
    Cart    → Product    (stock validation before adding item)
    Cart    → Order      (create order on checkout)

  Kafka (asynchronous events)
    order.placed          Cart    → Notification
    payment.status        Order   → Notification
    order.status          Order   → Notification
    user.registered       Auth    → Community  (UserRef sync)
```

---

### 3. Database Schema / ER Diagram

```
AUTH_DB
─────────────────────────────────────────────────────
users
  PK  user_id         BIGINT
      first_name      VARCHAR
      last_name       VARCHAR
      email_id        VARCHAR   UNIQUE NOT NULL
      password_hash   VARCHAR   NOT NULL
      role            ENUM(USER, ADMIN)
      image           VARCHAR
      created_at      TIMESTAMP

refresh_tokens
  PK  id              BIGINT
  FK  user_id ───────► users.user_id
      token           VARCHAR   UNIQUE
      expires_at      TIMESTAMP


COMMUNITY_DB
─────────────────────────────────────────────────────
user_refs                            ← lean mirror from Auth
  PK  user_id         BIGINT         (same ID as auth.users)
      display_name    VARCHAR
      image           VARCHAR

communities
  PK  community_id    BIGINT
  FK  owner_id ──────► user_refs.user_id
      name            VARCHAR   UNIQUE NOT NULL
      description     VARCHAR
      image           VARCHAR
      created_on      DATE

community_members                    ← M:M join table
  FK  community_id ──► communities.community_id
  FK  user_id      ──► user_refs.user_id
      PK (community_id, user_id)


PRODUCT_DB
─────────────────────────────────────────────────────
products
  PK  product_id      BIGINT
      user_id         BIGINT         ← seller (cross-service ref)
      community_id    BIGINT         ← scoping (cross-service ref)
      name            VARCHAR
      description     TEXT
      image           VARCHAR
      tag             VARCHAR
      color           VARCHAR
      size            VARCHAR
      price           DECIMAL(10,2)
      stock_count     INT
      status          ENUM(ACTIVE, SOLD_OUT, REMOVED)
      created_at      TIMESTAMP


CART_DB
─────────────────────────────────────────────────────
carts
  PK  cart_id         BIGINT
      user_id         BIGINT
      status          ENUM(OPEN, CHECKED_OUT, ABANDONED)
      created_at      TIMESTAMP
      updated_at      TIMESTAMP

cart_items
  PK  cart_item_id    BIGINT
  FK  cart_id ───────► carts.cart_id
      product_id      BIGINT
      community_id    BIGINT
      product_name    VARCHAR        ← snapshot at time of add
      quantity        INT
      price_at_add    DECIMAL(10,2)  ← snapshot at time of add
      added_at        TIMESTAMP


ORDER_DB
─────────────────────────────────────────────────────
payments
  PK  payment_id      BIGINT
      user_id         BIGINT
      order_id        BIGINT
      amount          DECIMAL(10,2)
      method          ENUM(CARD, UPI, WALLET, COD)
      status          ENUM(PENDING, SUCCESS, FAILED, REFUNDED)
      gateway_ref     VARCHAR        ← Stripe payment intent ID
      created_at      TIMESTAMP

orders
  PK  order_id        BIGINT
      user_id         BIGINT
  FK  payment_id ────► payments.payment_id
      cart_id         BIGINT
      total_amount    DECIMAL(10,2)
      status          ENUM(PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED)
      created_at      TIMESTAMP

order_items                          ← denormalised snapshot
  PK  order_item_id   BIGINT
  FK  order_id ──────► orders.order_id
      product_id      BIGINT
      product_name    VARCHAR        ← snapshot
      quantity        INT
      price_at_order  DECIMAL(10,2)  ← snapshot
```

---

## ✨ Features

### Communities
- Create and manage communities with a name, description, and cover image
- Explore communities you haven't joined yet
- Join or leave any community
- Community owners can add or remove members
- Each community is a fully isolated marketplace

### Products
- List products scoped to a specific community
- Only community members can view, list, or buy products
- Image, color, size, and tag support per product
- Real-time stock tracking with `ACTIVE / SOLD_OUT / REMOVED` states

### Cart
- Persistent cart per user — survives sessions
- Add, update quantity, and remove items
- Price and product name snapshots preserved at time of adding
- Abandoned cart tracking

### Orders & Payments
- Checkout validates stock before confirming
- Stripe-powered card payments
- Full order lifecycle: `PENDING → CONFIRMED → SHIPPED → DELIVERED`
- Payment status updated via Stripe webhook
- Order history with item-level price snapshots

### Auth & Security
- JWT access tokens with rotating refresh tokens
- BCrypt password hashing
- Role-based access control (`USER`, `ADMIN`)
- API Gateway is the single auth checkpoint — downstream services trust `X-User-Id` header

### Notifications
- Order confirmation email on successful checkout
- Payment success and failure emails
- Shipping and delivery notifications
- Fully decoupled via Kafka — zero synchronous coupling to Order or Payment services

---

## 🛠️ Setup & Installation

### Prerequisites

- Node.js 20+ and npm 9+
- Java 21+ and Maven 3.9+
- Docker and Docker Compose
- MySQL 8+
- Redis 7+

### Clone the repository

```bash
git clone https://github.com/nzefler/community-cart.git
cd community-cart
```

---

### Frontend (React)

```bash
cd frontend
npm install
cp .env.example .env        # set VITE_API_BASE_URL=http://localhost:8080
npm run dev                  # http://localhost:3000
```

---

### Backend — run each service in order

#### 1. Auth Service

```bash
cd auth-service
cp .env.example .env
npm install                  
mvn clean install -DskipTests
mvn spring-boot:run          # :8081
```

#### 2. Community Service

```bash
cd community-service
cp .env.example .env
mvn clean install -DskipTests
mvn spring-boot:run          # :8082
```

#### 3. Product Service

```bash
cd product-service
cp .env.example .env
mvn clean install -DskipTests
mvn spring-boot:run          # :8083
```

#### 4. Cart Service

```bash
cd cart-service
cp .env.example .env
mvn clean install -DskipTests
mvn spring-boot:run          # :8084
```

#### 5. Order Service

```bash
cd order-service
cp .env.example .env         # set STRIPE_SECRET_KEY
mvn clean install -DskipTests
mvn spring-boot:run          # :8085
```

#### 6. Notification Service

```bash
cd notification-service
cp .env.example .env         # set SMTP / SendGrid credentials
mvn clean install -DskipTests
mvn spring-boot:run          # :8086
```

#### 7. API Gateway

```bash
cd api-gateway
cp .env.example .env
mvn clean install -DskipTests
mvn spring-boot:run          # :8080
```

---

### Run everything with Docker Compose

```bash
# From the project root — starts all services, MySQL, Redis, Kafka, Zookeeper
docker-compose up --build
```

---

## 📡 API Documentation

All requests go through the API Gateway at `http://localhost:8080`.  
Include `Authorization: Bearer <token>` on all protected routes.

### Auth Service

```
POST   /api/auth/register
POST   /api/auth/login
POST   /api/auth/refresh
POST   /api/auth/logout
GET    /api/users/{id}
PUT    /api/users/{id}
DELETE /api/users/{id}
```

### Community Service

```
POST   /api/communities
GET    /api/communities
GET    /api/communities/{id}
PUT    /api/communities/{id}
DELETE /api/communities/{id}
POST   /api/communities/{id}/members/{userId}
DELETE /api/communities/{id}/members/{userId}
GET    /api/communities/{id}/members
GET    /api/users/{userId}/communities
```

### Product Service

```
POST   /api/products
GET    /api/products
GET    /api/products/{id}
PUT    /api/products/{id}
DELETE /api/products/{id}
GET    /api/communities/{communityId}/products
GET    /api/users/{userId}/products
PATCH  /api/products/{id}/stock
```

### Cart Service

```
GET    /api/carts/user/{userId}/open
GET    /api/carts/{id}
GET    /api/carts/user/{userId}
POST   /api/carts/{id}/items
PUT    /api/carts/{id}/items/{itemId}
DELETE /api/carts/{id}/items/{itemId}
DELETE /api/carts/{id}/items
POST   /api/carts/{id}/checkout
```

### Order Service

```
POST   /api/orders
GET    /api/orders
GET    /api/orders/{id}
GET    /api/orders/user/{userId}
PATCH  /api/orders/{id}/status
DELETE /api/orders/{id}
POST   /api/payments
GET    /api/payments/{id}
GET    /api/payments/user/{userId}
PATCH  /api/payments/{id}/status
```

---

## 📁 Folder Structure

```
community-cart/
│
├── frontend/                           # React application
│   ├── public/
│   ├── src/
│   │   ├── assets/
│   │   ├── components/                 # Shared UI components
│   │   ├── features/                   # Redux feature slices
│   │   │   ├── auth/
│   │   │   ├── community/
│   │   │   ├── product/
│   │   │   ├── cart/
│   │   │   └── order/
│   │   ├── hooks/
│   │   ├── pages/
│   │   ├── services/                   # Axios API clients
│   │   ├── store/
│   │   ├── types/
│   │   └── utils/
│   ├── .env.example
│   ├── package.json
│   └── vite.config.ts
│
├── api-gateway/                        # Spring Cloud Gateway  :8080
│   ├── src/main/java/com/nzefler/gateway/
│   │   ├── config/                     # Routes, JWT filter, CORS
│   │   └── filter/
│   └── pom.xml
│
├── auth-service/                       # Auth + User identity  :8081
│   ├── src/main/java/com/nzefler/auth/
│   │   ├── config/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── exception/
│   │   ├── model/                      # User, RefreshToken
│   │   ├── repository/
│   │   └── service/
│   └── pom.xml
│
├── community-service/                  # Communities + membership  :8082
│   ├── src/main/java/com/nzefler/community/
│   │   ├── config/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── exception/
│   │   ├── mapper/
│   │   ├── model/                      # Community, UserRef
│   │   ├── repository/
│   │   └── service/
│   └── pom.xml
│
├── product-service/                    # Product catalog  :8083
│   ├── src/main/java/com/nzefler/product/
│   │   ├── client/                     # CommunityClient (Feign)
│   │   ├── config/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── exception/
│   │   ├── model/                      # Product
│   │   ├── repository/
│   │   └── service/
│   └── pom.xml
│
├── cart-service/                       # Shopping cart  :8084
│   ├── src/main/java/com/nzefler/cart/
│   │   ├── client/                     # ProductClient (Feign)
│   │   ├── config/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── event/                      # Kafka publishers
│   │   ├── exception/
│   │   ├── model/                      # Cart, CartItem
│   │   ├── repository/
│   │   └── service/
│   └── pom.xml
│
├── order-service/                      # Orders + payments  :8085
│   ├── src/main/java/com/nzefler/order/
│   │   ├── config/                     # Stripe config
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── event/                      # Kafka publishers
│   │   ├── exception/
│   │   ├── model/                      # Order, OrderItem, Payment
│   │   ├── repository/
│   │   └── service/
│   └── pom.xml
│
├── notification-service/               # Email notifications  :8086
│   ├── src/main/java/com/nzefler/notification/
│   │   ├── config/                     # Kafka consumer, mail config
│   │   ├── event/                      # Event DTOs
│   │   ├── listener/                   # Kafka @KafkaListener handlers
│   │   ├── service/
│   │   └── template/                   # Thymeleaf email templates
│   └── pom.xml
│
├── common/                             # Shared library (event DTOs, utils)
│   ├── src/main/java/com/nzefler/common/
│   │   ├── dto/
│   │   ├── event/
│   │   └── exception/
│   └── pom.xml
│
├── docker-compose.yml
├── .env.example
└── README.md
```

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch — `git checkout -b feature/your-feature`
3. Commit your changes — `git commit -m 'feat: add your feature'`
4. Push to the branch — `git push origin feature/your-feature`
5. Open a pull request

---

## 📄 License

MIT License — see [LICENSE](LICENSE) for details.
