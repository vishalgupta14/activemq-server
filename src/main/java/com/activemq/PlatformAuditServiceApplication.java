package com.activemq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJms
@EnableScheduling
@EntityScan("com.activemq.entities")
public class PlatformAuditServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformAuditServiceApplication.class, args);
    }

}
