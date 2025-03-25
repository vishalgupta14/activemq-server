package com.activemq.scheduler.schedule;

import com.activemq.scheduler.service.DataProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(name = "scheduler.switch.enabled", havingValue = "true", matchIfMissing = false)
public class DataScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(DataScheduler.class);
    @Autowired
    private AppConfig appConfig;

    @Value("${scheduler.time}")
    private String scheduleDays;

    @Autowired
    private DataProcessingService dataProcessingService;

    @Scheduled(fixedDelayString = "#{T(java.time.Duration).parse('${scheduler.time}').toMillis()}")
    public void processAuditData() {
        if (appConfig.getPath().isEmpty()) {
            LOG.info("File Path not provided. Unable to perform the operation without the required file path.");
            return;
        }
        LOG.info("Scheduler started. Will execute every {} days.", Duration.parse(scheduleDays).toDays());
        List<String> auditTables = Arrays.stream(appConfig.getCsvSaveTables()).collect(Collectors.toList());
        int dataSaveDays = appConfig.getArchiveDataDays();
        auditTables.stream().filter(table -> !table.isEmpty()).forEach(tableName -> dataProcessingService.fetchAuditData(tableName, dataSaveDays, appConfig.getPath()));

    }

}
