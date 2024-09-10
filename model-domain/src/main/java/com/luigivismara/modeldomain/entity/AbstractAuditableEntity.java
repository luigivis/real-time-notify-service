package com.luigivismara.modeldomain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractAuditableEntity extends StandardEntitiesRows implements AuditableEntity {

    @JsonIgnore
    @Override
    public String getOldValues(Object oldValue) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(oldValue);
    }

    @JsonIgnore
    @Override
    public String getNewValues() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }

    @JsonIgnore
    public abstract UUID getId();
}