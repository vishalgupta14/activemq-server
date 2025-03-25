package com.activemq.async;

public abstract class AuditMessageListener {
    public static final String ENTITY = "entity";

    protected abstract void process(String message);

}
