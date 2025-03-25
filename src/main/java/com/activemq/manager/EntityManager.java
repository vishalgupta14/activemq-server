package com.activemq.manager;

import com.google.gson.JsonObject;
import com.activemq.config.DatabaseTypeConfig;
import com.activemq.entities.EntityAudit;
import com.activemq.factory.EntityAuditServiceFactory;
import com.activemq.factory.ErrorServiceFactory;
import com.activemq.service.EntityAuditService;
import com.activemq.service.ErrorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.activemq.async.AuditMessageListener.ENTITY;
import static com.activemq.manager.Manager.saveError;

/**
 * @author vishalgupta
 */
@Service
@Qualifier(ENTITY)
public class EntityManager implements Manager {

    public static final String CONDITION = "condition";
    private final DatabaseTypeConfig databaseTypeConfig;
    private final EntityAuditServiceFactory entityAuditServiceFactory;
    private final ErrorServiceFactory errorServiceFactory;

    public EntityManager(DatabaseTypeConfig databaseTypeConfig, EntityAuditServiceFactory entityAuditServiceFactory, ErrorServiceFactory errorServiceFactory) {
        this.databaseTypeConfig = databaseTypeConfig;
        this.entityAuditServiceFactory = entityAuditServiceFactory;
        this.errorServiceFactory = errorServiceFactory;
    }

    public void insert(JsonObject jsonObject) {
        List<String> database = databaseTypeConfig.getProcessedMap().get(ENTITY);
        database.forEach(db -> {
            EntityAuditService entityAuditService = entityAuditServiceFactory.getService(db);
            ErrorService errorService = errorServiceFactory.getService(db);
            try {
                EntityAudit audit = Manager.parseFromJson(jsonObject, EntityAudit.class);
                entityAuditService.saveAudit(audit);
            } catch (Exception e) {
                saveError(errorService, jsonObject, e);
            }
        });
    }
}
