package com.activemq.service;

import com.activemq.entities.EntityAudit;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EntityAuditService {
    List<EntityAudit> getAllAudits();

    Optional<EntityAudit> getAuditById(Long id);

    EntityAudit saveAudit(EntityAudit audit);

    void deleteAudit(Long id);
}
