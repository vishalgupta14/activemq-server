package com.activemq.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.activemq.entities.Error;
import com.activemq.service.ErrorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author vishalgupta
 */
public interface Manager {

    Logger log = LoggerFactory.getLogger(Manager.class);
    String APPLICATION = "APPLICATION";
    String USER = "USER";
    String ORGANIZATION = "ORGANIZATION";
    String PROCESS = "PROCESS";
    String ADDITIONAL_DATA = "additionalData";
    String MODIFIED_BY = "modifiedBy";

    String INSERT = "INSERT";

    static <T> T parseFromJson(JsonObject jsonObject, Class<T> clazz) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.readValue(jsonObject.toString(), clazz);
    }

    static void saveError(ErrorService errorService, JsonObject jsonObject, Exception e) {
        try {
            log.error("Error: {}, Json: {}", e.getMessage(), jsonObject.toString());
            Error error = new Error();
            error.setJsonMessage(jsonObject.toString());
            error.setMessage(e.getMessage());
            errorService.saveError(error);
        } catch (Exception ex) {
            log.error("Error in saveError method: {}, Json: {}", e.getMessage(), jsonObject.toString());
        }
    }

    static String defaultIfNull(JsonObject data, String field) {
        if (data.has(field) && !data.get(field).isJsonNull()) {
            return data.get(field).getAsString();
        } else {
            return "";
        }
    }

    void insert(JsonObject jsonObject);

    default void delete(JsonObject jsonObject) {
    }

}
