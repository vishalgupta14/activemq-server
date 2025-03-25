package com.activemq.service.postgres;

import com.activemq.entities.Error;
import com.activemq.repository.ErrorRepository;
import com.activemq.service.ErrorService;
import com.activemq.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Qualifier("POSTGRES")
public class ErrorPostgresService implements ErrorService, SchedulerService {

    @Autowired
    private ErrorRepository errorRepository;

    @Override
    public Error saveError(Error error) {
        return errorRepository.save(error);
    }

    @Override
    public Page<?> findByPerformedOnAfter(Date date, Pageable pageable) {
        return errorRepository.findByInsertedOnAfter(date, pageable);
    }

    @Override
    public void deleteByPerformedOnAfter(Date date) {
        errorRepository.deleteByInsertedOnAfter(date);
    }

}

