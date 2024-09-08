package com.luigivismara.modeldomain.entity;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.UUID;

public interface AuditableEntity {

    UUID getId();

    String getOldValues(Object oldValue) throws JsonProcessingException;

    String getNewValues() throws JsonProcessingException;
}
