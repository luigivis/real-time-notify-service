package com.luigivismara.modeldomain.entity;

import java.util.UUID;

public interface AuditableEntity {

    UUID getId();

    String getOldValues();

    String getNewValues();
}
