package com.activemq.model;

import java.util.Date;

public class EntityAudit {

    private Long entityId;
    private String data;
    private Date performedOn;

    public Long getEntityId() {
        return entityId;
    }

    public EntityAudit setEntityId(Long entityId) {
        this.entityId = entityId;
        return this;
    }

    public String getData() {
        return data;
    }

    public EntityAudit setData(String data) {
        this.data = data;
        return this;
    }

    public Date getPerformedOn() {
        return performedOn;
    }

    public EntityAudit setPerformedOn(Date performedOn) {
        this.performedOn = performedOn;
        return this;
    }
}