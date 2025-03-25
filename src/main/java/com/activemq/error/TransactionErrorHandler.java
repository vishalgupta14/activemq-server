package com.activemq.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ErrorHandler;

public class TransactionErrorHandler implements ErrorHandler {
    private static final Logger log = LoggerFactory.getLogger(TransactionErrorHandler.class);

    @Override
    public void handleError(Throwable t) {
        log.error("An error has occurred in the transaction");
        t.printStackTrace();
    }
}
