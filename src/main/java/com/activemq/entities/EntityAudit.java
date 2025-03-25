package com.activemq.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;

import static com.activemq.entities.ModelConstant.*;

@Data
@Entity
@Table(name = EntityAudit.TABLE_NAME)
public class EntityAudit {

    public static final String TABLE_NAME = "entity";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID)
    private Long id;

    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonProperty(ENTITY_ID)
    @Column(name = ENTITY_ID)
    private Long entityId;

    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonProperty(DATA)
    @Lob
    @Column(name = DATA, columnDefinition = "LONGTEXT")
    private String data;

    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonProperty(PERFORMED_ON)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss z")
    @Column(name = PERFORMED_ON)
    @Temporal(TemporalType.TIMESTAMP)
    private Date performedOn;
}
