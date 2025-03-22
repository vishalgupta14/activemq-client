package com.activemq.model;

import java.util.Date;

public class EntityAudit {

    private Long organizationId;
    private Long applicationId;
    private String applicationName;
    private Long flowId;
    private String flowName;
    private Long entityId;
    private String entityName;
    private String entityType;
    private boolean isDeployed;
    private String data;
    private Long performedById;
    private Date performedOn;
    private String actionType;

    public Long getOrganizationId() {
        return organizationId;
    }

    public EntityAudit setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
        return this;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public EntityAudit setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
        return this;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public EntityAudit setApplicationName(String applicationName) {
        this.applicationName = applicationName;
        return this;
    }

    public Long getFlowId() {
        return flowId;
    }

    public EntityAudit setFlowId(Long flowId) {
        this.flowId = flowId;
        return this;
    }

    public String getFlowName() {
        return flowName;
    }

    public EntityAudit setFlowName(String flowName) {
        this.flowName = flowName;
        return this;
    }

    public Long getEntityId() {
        return entityId;
    }

    public EntityAudit setEntityId(Long entityId) {
        this.entityId = entityId;
        return this;
    }

    public String getEntityName() {
        return entityName;
    }

    public EntityAudit setEntityName(String entityName) {
        this.entityName = entityName;
        return this;
    }

    public String getEntityType() {
        return entityType;
    }

    public EntityAudit setEntityType(String entityType) {
        this.entityType = entityType;
        return this;
    }

    public boolean isDeployed() {
        return isDeployed;
    }

    public EntityAudit setDeployed(boolean deployed) {
        isDeployed = deployed;
        return this;
    }

    public String getData() {
        return data;
    }

    public EntityAudit setData(String data) {
        this.data = data;
        return this;
    }

    public Long getPerformedById() {
        return performedById;
    }

    public EntityAudit setPerformedById(Long performedById) {
        this.performedById = performedById;
        return this;
    }

    public Date getPerformedOn() {
        return performedOn;
    }

    public EntityAudit setPerformedOn(Date performedOn) {
        this.performedOn = performedOn;
        return this;
    }

    public String getActionType() {
        return actionType;
    }

    public EntityAudit setActionType(String actionType) {
        this.actionType = actionType;
        return this;
    }
}