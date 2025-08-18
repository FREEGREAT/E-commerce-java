# Example of properties with Connecting to PostgreSQL or MongoDB in Spring Boot


## 1. Connecting to PostgreSQL

To connect to PostgreSQL, JDBC URL and authentication parameters are used.  
Add the following settings to your `application.properties` file:

```properties
# PostgreSQL
spring.datasource.url=jdbc:postgresql://<HOST>:<PORT>/<DB_NAME>
spring.datasource.username=neondb
spring.datasource.password=npg
spring.jpa.database=POSTGRESQL
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

**Parameter explanations:**
- `spring.datasource.url` – Path to the PostgreSQL database.
- `spring.datasource.username` – Database username.
- `spring.datasource.password` – Database password.
- `spring.jpa.database` – Specifies the database type (`POSTGRESQL`).
- `spring.jpa.show-sql` – Enables SQL query logging in the console.
- `spring.jpa.hibernate.ddl-auto` – Automatically creates/updates tables (`update`, `create`, `create-drop`, `none`).
- `spring.jpa.database-platform` – Hibernate dialect for PostgreSQL.

**Example URL for local database:**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/mydb
```

---

## 2. Connecting to MongoDB

MongoDB is connected using a URI string.  
Add the following to your `application.properties` file:

```properties
# MongoDB
spring.data.mongodb.uri=mongodb+srv://<USERNAME>:<PASSWORD>@<CLUSTER_URL>/
spring.data.mongodb.database=userdb
```

**Parameter explanations:**
- `spring.data.mongodb.uri` – Full URI for connection (with login, password, and host).
- `spring.data.mongodb.database` – The database name in MongoDB.

**Example for local MongoDB:**
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/
spring.data.mongodb.database=userdb
```

---

## 3. Eureka config example

Server config:
```properties
spring.application.name=eureka-server
server.port=8761

eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
```

Client config (user-service for example):
```properties
spring.application.name=user-service

server.port=8081

#Mongo
spring.data.mongodb.uri=mongodb://localhost:27017/userdb
spring.data.mongodb.database=userdb

#Eureka
eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true

```