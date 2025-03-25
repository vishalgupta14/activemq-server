# ActiveMQ JMS Server

This ActiveMQ server application consumes JMS messages from a specified queue and processes audit messages, backups, and handles scenarios for failed message retries using scheduled tasks. The server integrates seamlessly with Apache ActiveMQ Artemis and PostgreSQL for persistent storage.

---

## 🚀 Features
- Consumes JMS messages from ActiveMQ Artemis queues.
- Scheduled processing of failed JMS messages.
- Conditional activation of message listeners and schedulers.
- Automatic retry and cleanup mechanisms.
- JSON-based message processing.
- Configurable schedules and batch processing capabilities.

---

## 🧱 Requirements
- Java 17+
- Spring Boot 3+
- Apache ActiveMQ Artemis (via Docker)
- PostgreSQL Database (via Docker)
- Maven

---

## 🐳 Docker Setup

### 🔸 ActiveMQ Artemis
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

- Web Console: [http://localhost:8161](http://localhost:8161)  
  Username: `admin`, Password: `admin`

### 🔸 PostgreSQL
```bash
docker run -d \
  --name postgres-jms \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=jmsdb \
  -p 5433:5432 \
  postgres
```

---

## ⚙️ Configuration (`application.properties`)
```properties
# ActiveMQ Artemis
spring.artemis.mode=native
spring.artemis.host=localhost
spring.artemis.port=61616
spring.artemis.user=admin
spring.artemis.password=admin

# JMS Queue Configuration
queue.name=entity.queue

# PostgreSQL Configuration
spring.datasource.url=jdbc:postgresql://localhost:5433/jmsdb
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update

# Scheduler Configuration
entity.listener.enabled=true
failed.message.processor.delay=30000
```

---

## 🔄 Included Components
- **JMS Consumer Listener**
    - Listens and processes messages from the specified queue.

- **Failed JMS Message Scheduler**
    - Automatically retries processing of messages that initially failed.

- **Backup and Recovery Scheduler**
    - Periodically backs up critical messages or audit logs.

---

## ✏️ Conditional Listener and Scheduler Setup

Components can be conditionally activated:

Enable JMS Listener and Scheduler:
```properties
entity.listener.enabled=true
```

Disable JMS Listener and Scheduler:
```properties
entity.listener.enabled=false
```

---

## 📌 JMS Listener Example

```java
@Component
@ConditionalOnProperty(name = "entity.listener.enabled", havingValue = "true")
public class AuditMessageListener {

    @JmsListener(destination = "${queue.name}")
    public void receive(String message) {
        // Message processing logic
    }
}
```

---

## 🧪 Running Tests

Run unit tests:
```bash
mvn test
```

---

## 📦 Packaging and Deployment

To package and install locally:
```bash
mvn clean install
```

To run the application:
```bash
mvn spring-boot:run
```

---

## 📫 Contributions

Contributions are welcome! Feel free to submit pull requests or open issues to suggest improvements or report bugs.

