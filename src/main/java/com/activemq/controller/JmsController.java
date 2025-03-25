package com.activemq.controller;

import com.activemq.factory.AuditFactory;
import com.activemq.model.EntityAudit;
import com.activemq.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jms")
public class JmsController {
    @Autowired
    private AuditFactory factory;
    private final MessageService messageService;

    public JmsController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/send")
    public String sendMessage() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        EntityAudit audit = factory.createEntityAudit("TestEntity", 100L, "some-data");
        String message = objectMapper.writeValueAsString(audit);
        messageService.send("audit.entity.queue", message);
        return "Message sent to queue: audit.entity.queue";
    }
}
