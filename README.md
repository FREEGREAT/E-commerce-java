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

## 3. Additional Information

**Maven dependencies (`pom.xml`) for working with PostgreSQL and MongoDB:**
```xml
    <!-- PostgreSQL -->
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
    </dependency>

    <!-- Spring Data JPA (using with psql) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- MongoDB -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-mongodb</artifactId>
    </dependency>
```