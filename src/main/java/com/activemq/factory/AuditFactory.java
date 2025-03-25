package com.activemq.factory;

import com.activemq.model.EntityAudit;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AuditFactory {

    public EntityAudit createEntityAudit(String entityName, Long entityId, String data) {
        return new EntityAudit()
                .setEntityId(entityId)
                .setData(data)
                .setPerformedOn(new Date());
    }

}