package com.activemq.scheduler.service;

import com.activemq.scheduler.schedule.AppConfig;
import com.activemq.service.SchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

@Service
public class DataProcessingService {

    private static final Logger LOG = LoggerFactory.getLogger(DataProcessingService.class);
    private final Map<String, SchedulerService> serviceMap = new HashMap<>();
    @Autowired
    private AppConfig appConfig;

    @Autowired
    public DataProcessingService(List<SchedulerService> auditServices) {
        auditServices.forEach(service -> {
            String serviceName = service.getClass().getSimpleName();
            serviceMap.put(serviceName, service);
        });
    }

    public SchedulerService getService(String serviceName) {
        return serviceMap.entrySet().stream().filter(entry -> entry.getKey().toLowerCase().contains(serviceName)).map(Map.Entry::getValue).findFirst().orElse(null);
    }

    public void fetchAuditData(String tableName, int dataSaveDays, String filePath) {
        LocalDateTime purgeAfterDay = LocalDateTime.now().minusDays(dataSaveDays);
        Date purgeAfterDate = Date.from(purgeAfterDay.atZone(ZoneId.systemDefault()).toInstant());
        SchedulerService schedulerService = getService(tableName.toLowerCase());
        if (schedulerService == null) {
            throw new IllegalArgumentException("Scheduler Service not present for table : " + tableName);
        }

        AtomicBoolean errorOccurred = new AtomicBoolean(false);
        int BATCH_SIZE = appConfig.getBatchSize();

        IntStream.iterate(0, i -> i + 1).mapToObj(i -> schedulerService.findByPerformedOnAfter(purgeAfterDate, PageRequest.of(i, BATCH_SIZE))).anyMatch(entityPage -> {
            if (entityPage.hasContent()) {
                boolean dataWritten = CsvWriter.writeListToCsv(entityPage.getContent(), tableName, entityPage, filePath);
                if (!dataWritten) {
                    errorOccurred.set(true);
                    LOG.error("Error occurred while writing data to CSV for table: " + tableName);
                }
                return false;
            } else {
                return true;
            }
        });
        if (!errorOccurred.get()) {
            LOG.info("Data fetched and CSV generated successfully for the '{}' table.", tableName);
            List<String> deletedTableNames = Arrays.asList(appConfig.getDeleteTables());
            deletedTableNames.stream().filter(deleteTable -> deleteTable.equalsIgnoreCase(tableName)).forEach(deleteTable -> {
                deleteAuditData(purgeAfterDate, schedulerService);
                LOG.info("Data deleted successfully for the '{}' table.", tableName);
            });
        }
    }

    public void deleteAuditData(Date deleteAfter, SchedulerService schedulerService) {
        if (!deleteAfter.equals(null) || !schedulerService.equals(null)) {
            schedulerService.deleteByPerformedOnAfter(deleteAfter);
        } else {
            throw new IllegalArgumentException("Data is not present ");
        }

    }

}

