# ActiveMQ JMS Client

## Entity Audit Messaging Library
This library allows you to send structured audit messages (like `Entity`) to an Apache ActiveMQ Artemis queue using Spring Boot and optionally store failed sends to PostgreSQL.

We are using a **Circuit Breaker** to handle situations when ActiveMQ is down or the queue is full. More details can be found in the provided ZIP archive.

🚀 **Features**
- Send audit logs to JMS queues (ActiveMQ Artemis)
- Circuit Breaker integration for improved resilience
- Fallback mechanism to PostgreSQL when ActiveMQ is unavailable or queue capacity is reached
- Easily build audit models via factory
- Includes unit tests with JUnit & Mockito

🧱 **Requirements**
- Java 17+
- Maven
- Docker (for Artemis & PostgreSQL setup)

🐳 **Docker Setup**

🔸 **ActiveMQ Artemis**
```bash
docker run -d \
  --name artemis \
  -p 61616:61616 \
  -p 8161:8161 \
  -e ARTEMIS_USER=admin \
  -e ARTEMIS_PASSWORD=admin \
  -e ANONYMOUS_LOGIN=false \
  apache/activemq-artemis:latest
```
Web Console: [http://localhost:8161](http://localhost:8161)  
Username: `admin`, Password: `admin`

🔸 **PostgreSQL (optional - for failed audit storage)**
```bash
docker run -d \
  --name postgres-jms \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=jmsdb \
  -p 5433:5432 \
  postgres
```

🛠️ **Configuration (`application.properties`)**
```properties
# ActiveMQ Artemis
spring.artemis.mode=native
spring.artemis.host=localhost
spring.artemis.port=61616
spring.artemis.user=admin
spring.artemis.password=admin

# Audit Queue Name
audit.queue.name=audit.entity.queue

# PostgreSQL (if storing failed messages)
spring.datasource.url=jdbc:postgresql://localhost:5432/jmsdb
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
```

🧪 **Run Tests**
```bash
mvn test
```

✉️ **Example Usage**
```java
@Autowired
private EntityAuditQueuePublisher publisher;

@Autowired
private AuditFactory auditFactory;

EntityAudit audit = auditFactory.createEntityAudit("Customer", 101L, "created new record");
publisher.publish(audit);
```

✅ **Verifying Queue Messages (via Web Console)**
- Go to: [http://localhost:8161](http://localhost:8161)
- Login as `admin`
- Click on **Queues**
- Select `audit.entity.queue`
- Browse or purge messages

📦 **Packaging as Library**
To build and install the JAR locally:
```bash
mvn clean install
```
In another project:
```xml
<dependency>
  <groupId>com.platform.audit</groupId>
  <artifactId>audit-messaging-lib</artifactId>
  <version>1.0.0</version>
</dependency>
```

📫 **Contributions**
Feel free to fork and contribute with improvements, bug fixes, or new features.

