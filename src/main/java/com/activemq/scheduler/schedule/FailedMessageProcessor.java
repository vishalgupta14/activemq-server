package com.activemq.scheduler.schedule;

import com.activemq.entities.FailedJmsMessage;
import com.activemq.manager.Manager;
import com.activemq.repository.FailedJmsMessageRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.activemq.async.AuditMessageListener.ENTITY;

@Component
@ConditionalOnProperty(name = "failed.message.scheduler.switch.enabled", havingValue = "true", matchIfMissing = true)
public class FailedMessageProcessor {

    private static final Logger log = LoggerFactory.getLogger(FailedMessageProcessor.class);
    private final Gson gson = new Gson();

    @Autowired
    private FailedJmsMessageRepository repository;

    @Autowired
    @Qualifier(ENTITY)
    private Manager manager;

    @Scheduled(fixedDelayString = "${failed.message.processor.delay:30000}")
    @Transactional
    public void processFailedMessages() {
        List<FailedJmsMessage> failedMessages = repository.findTop10ByOrderByFailedAtAsc();

        if (failedMessages.isEmpty()) {
            log.info("No failed JMS messages to process.");
            return;
        }

        log.info("Processing {} failed JMS messages", failedMessages.size());

        for (FailedJmsMessage failedMessage : failedMessages) {
            try {
                JsonObject jsonObject = gson.fromJson(failedMessage.getMessage(), JsonObject.class);
                manager.insert(jsonObject);

                repository.delete(failedMessage);
                log.info("Processed and deleted message ID: {}", failedMessage.getId());
            } catch (Exception e) {
                log.error("Error processing message ID: {}", failedMessage.getId(), e);
            }
        }
    }
}