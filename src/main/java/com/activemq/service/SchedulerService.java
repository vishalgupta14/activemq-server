package com.activemq.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public interface SchedulerService {

    Page<?> findByPerformedOnAfter(Date date, Pageable pageable);

    @Transactional
    void deleteByPerformedOnAfter(Date date);

}
