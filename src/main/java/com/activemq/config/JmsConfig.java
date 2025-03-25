package com.activemq.config;

import com.activemq.error.TransactionErrorHandler;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

@Configuration
public class JmsConfig {

    @Bean
    public CachingConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory =
                new ActiveMQConnectionFactory("tcp://localhost:61616");
        activeMQConnectionFactory.setUser("admin");
        activeMQConnectionFactory.setPassword("admin");

        return new CachingConnectionFactory(activeMQConnectionFactory);
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(
            CachingConnectionFactory connectionFactory,
            TransactionErrorHandler transactionErrorHandler) {

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setErrorHandler(transactionErrorHandler);
        return factory;
    }

    @Bean
    public TransactionErrorHandler transactionErrorHandler() {
        return new TransactionErrorHandler();
    }
}
