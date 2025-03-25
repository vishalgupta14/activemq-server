package com.activemq.service.postgres;

import com.activemq.entities.EntityAudit;
import com.activemq.repository.EntityAuditRepository;
import com.activemq.service.EntityAuditService;
import com.activemq.service.SchedulerService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Qualifier("POSTGRES")
public class EntityEntityAuditPostgresService implements EntityAuditService, SchedulerService {

    private final EntityAuditRepository entityAuditRepository;
    private final EntityManager entityManager;

    @Autowired
    public EntityEntityAuditPostgresService(EntityAuditRepository entityAuditRepository, EntityManager entityManager) {
        this.entityAuditRepository = entityAuditRepository;
        this.entityManager = entityManager;
    }

    public List<EntityAudit> getAllAudits() {
        return entityAuditRepository.findAll();
    }

    public Optional<EntityAudit> getAuditById(Long id) {
        return entityAuditRepository.findById(id);
    }

    public EntityAudit saveAudit(EntityAudit audit) {
        return entityAuditRepository.save(audit);
    }

    public void deleteAudit(Long id) {
        entityAuditRepository.deleteById(id);
    }


    @Override
    public Page<EntityAudit> findByPerformedOnAfter(Date date, Pageable pageable) {
        return entityAuditRepository.findByPerformedOnAfter(date, pageable);
    }

    @Override
    public void deleteByPerformedOnAfter(Date date) {
        entityAuditRepository.deleteByPerformedOnAfterAndActionTypeNotEqual(date);
    }

}
