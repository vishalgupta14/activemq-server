package com.activemq.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

import static com.activemq.entities.ModelConstant.*;

@Data
@Entity
@Table(name = Error.TABLE_NAME)
public class Error {

    public static final String TABLE_NAME = "error";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = MESSAGE)
    private String message;

    @Lob
    @Column(name = DETAILS, columnDefinition = "LONGTEXT")
    private String details;

    @Lob
    @Column(name = JSON_MESSAGE, columnDefinition = "LONGTEXT")
    private String jsonMessage;

    @Column(name = INSERTED_ON, nullable = false, updatable = false)
    private Date insertedOn;


    @PrePersist
    public void prePersist() {
        insertedOn = new Date();
        details = trimToLength(details, 2000);
        jsonMessage = trimToLength(jsonMessage, 2500);
    }

    private String trimToLength(String value, int maxLength) {
        if (value != null && value.length() > maxLength) {
            return value.substring(0, maxLength);
        }
        return value;
    }
}
