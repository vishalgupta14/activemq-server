package com.activemq.factory;

import com.activemq.service.EntityAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EntityAuditServiceFactory {

    @Autowired
    @Qualifier("POSTGRES")
    private EntityAuditService auditMySQLService;

    public EntityAuditService getService(String databaseType) {
        if ("POSTGRES".equalsIgnoreCase(databaseType)) {
            return auditMySQLService;
        } else {
            throw new IllegalArgumentException("Unsupported database type: " + databaseType);
        }
    }
}
