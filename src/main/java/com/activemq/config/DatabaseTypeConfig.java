package com.activemq.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@ConfigurationProperties(prefix = "database.type")
public class DatabaseTypeConfig {

    private final Map<String, List<String>> processedMap = new HashMap<>();
    private Map<String, String> map = new HashMap<>();

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
        processMap();
    }

    public Map<String, List<String>> getProcessedMap() {
        return processedMap;
    }

    private void processMap() {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            List<String> valueList = Stream.of(entry.getValue().split(","))
                    .collect(Collectors.toList());
            processedMap.put(entry.getKey(), valueList);
        }
    }
}
