package com.activemq.factory;

import com.activemq.service.ErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ErrorServiceFactory {

    @Autowired
    @Qualifier("POSTGRES")
    private ErrorService errorMySQLService;


    public ErrorService getService(String databaseType) {
        if ("POSTGRES".equalsIgnoreCase(databaseType)) {
            return errorMySQLService;
        } else {
            throw new IllegalArgumentException("Unsupported database type: " + databaseType);
        }
    }
}
