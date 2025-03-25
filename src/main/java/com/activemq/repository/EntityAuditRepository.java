package com.activemq.repository;

import com.activemq.entities.EntityAudit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.activemq.entities.ModelConstant.ACTION_TYPE;
import static com.activemq.entities.ModelConstant.PERFORMED_ON;

@Repository
public interface EntityAuditRepository extends JpaRepository<EntityAudit, Long> {


    Page<EntityAudit> findByPerformedOnAfter(Date date, Pageable pageable);

    @Modifying
    @Transactional
    @Query("DELETE FROM  EntityAudit ca WHERE ca." + PERFORMED_ON + "> :date")
    void deleteByPerformedOnAfterAndActionTypeNotEqual(@Param("date") Date date);

}
