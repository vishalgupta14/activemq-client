package com.activemq.service;

import com.activemq.model.EntityAudit;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EntityAuditQueuePublisher {

    @Autowired
    private MessageService messageService;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${audit.queue.name:audit.entity.queue}")
    private String auditQueueName;

    @Async
    public void publish(EntityAudit audit) {
        try {
            String payload = objectMapper.writeValueAsString(audit);
            messageService.send(auditQueueName, payload);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}