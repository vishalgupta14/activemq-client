package com.activemq;

import com.activemq.factory.AuditFactory;
import com.activemq.model.EntityAudit;
import com.activemq.service.EntityAuditQueuePublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.activemq.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EntityAuditTest {

    @Mock
    private MessageService messageService;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private EntityAuditQueuePublisher publisher;

    private AuditFactory factory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        factory = new AuditFactory();
    }

    @Test
    public void testCreateEntityAudit() {
        EntityAudit audit = factory.createEntityAudit("TestEntity", 100L, "some-data");
        assertEquals(100L, audit.getEntityId());
        assertEquals("some-data", audit.getData());
        assertNotNull(audit.getPerformedOn());
    }

    @Test
    public void testPublishSuccess() throws Exception {
        EntityAudit audit = factory.createEntityAudit("TestEntity", 100L, "some-data");
        String jsonPayload = "{\"entityName\":\"TestEntity\"}";

        // Mock JSON conversion
        when(objectMapper.writeValueAsString(any())).thenReturn(jsonPayload);

        // Inject queue name manually for unit test
        // Because @Value injection doesn't happen in plain unit test
        var field = EntityAuditQueuePublisher.class.getDeclaredField("auditQueueName");
        field.setAccessible(true);
        field.set(publisher, "test.queue");

        // Call method
        publisher.publish(audit);

        // Verify interaction
        verify(messageService, times(1)).send(eq("test.queue"), eq(jsonPayload));
    }

    @Test
    public void testPublishJsonProcessingException() throws Exception {
        EntityAudit audit = factory.createEntityAudit("TestEntity", 100L, "bad-data");

        when(objectMapper.writeValueAsString(any())).thenThrow(new JsonProcessingException("error") {});

        assertDoesNotThrow(() -> publisher.publish(audit));
    }
}