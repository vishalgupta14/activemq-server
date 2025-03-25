package com.activemq.async;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.activemq.manager.Manager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;


@Component
@ConditionalOnProperty(name = "entity.listener.enabled", havingValue = "true", matchIfMissing = false)
public class EntityAuditMessageListener extends AuditMessageListener {

    private static final Logger log = LoggerFactory.getLogger(EntityAuditMessageListener.class);

    @Autowired
    @Qualifier(ENTITY)
    private Manager manager;

    @JmsListener(destination = "${queue.name}")
    public void process(String message) {
        log.info("Message received");
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(message, JsonObject.class);
        manager.insert(jsonObject);
    }
}

