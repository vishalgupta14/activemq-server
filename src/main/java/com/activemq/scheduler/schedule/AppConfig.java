package com.activemq.scheduler.schedule;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${archive.data.days}")
    private int archiveDataDays;
    @Value("${csv.table}")
    private String[] csvSaveTables;
    @Value("${delete.table}")
    private String[] deleteTables;

    @Value("${scheduler.path}")
    private String path;

    @Value("${scheduler.batch.size}")
    private int batchSize;

    public int getArchiveDataDays() {
        return archiveDataDays;
    }

    public String[] getCsvSaveTables() {
        return csvSaveTables;
    }

    public String[] getDeleteTables() {
        return deleteTables;
    }

    public String getPath() {
        return path;
    }

    public int getBatchSize() {
        return batchSize;
    }
}
