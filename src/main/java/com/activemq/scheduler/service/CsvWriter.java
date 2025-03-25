package com.activemq.scheduler.service;

import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CsvWriter {
    private static final Logger LOG = LoggerFactory.getLogger(CsvWriter.class);

    public static <T> boolean writeListToCsv(List<T> objectList, String tableName, Page<?> page, String csvFilePath) {
        String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        Path folderPath = Paths.get(csvFilePath, currentDate);
        try {
            Files.createDirectories(folderPath);
        } catch (IOException e) {
            LOG.error("Error creating directory: {}", folderPath, e);
            return false;
        }
        String fileName = tableName + "_audit_table.csv";
        String filePath = folderPath.resolve(fileName).toString();
        if (objectList == null || objectList.isEmpty()) {
            LOG.info("No records found for the '{}' table. Nothing was written.", tableName);
            return false;
        }
        try (Writer writer = Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
             CSVWriter csvWriter = new CSVWriter(writer)) {

            if (page.getNumber() == 0 && objectList.size() > 0) {
                writeCsvHeader(objectList.get(0), csvWriter);
            }

            for (T object : objectList) {
                writeCsvRow(object, csvWriter);
            }
            LOG.info("Data for the '{}' table successfully written to the CSV file.", tableName);
            return true;
        } catch (IOException | IllegalAccessException e) {
            LOG.error("Error writing CSV file: {}", filePath, e);
        }
        return false;
    }

    public static <T> void writeCsvHeader(T object, CSVWriter csvWriter) throws IllegalAccessException {
        Field[] fields = object.getClass().getDeclaredFields();
        String[] header = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            header[i] = fields[i].getName();
        }
        csvWriter.writeNext(header);
    }

    private static <T> void writeCsvRow(T object, CSVWriter csvWriter) throws IllegalAccessException {
        Field[] fields = object.getClass().getDeclaredFields();
        String[] row = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            Object value = fields[i].get(object);
            row[i] = value != null ? value.toString() : "";
        }
        csvWriter.writeNext(row);
    }
}
