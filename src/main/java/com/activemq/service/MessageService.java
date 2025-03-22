package com.activemq.service;

import com.activemq.entity.FailedJmsMessage;
import com.activemq.repository.FailedJmsMessageRepository;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import jakarta.annotation.PostConstruct;
import jakarta.jms.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@EnableJms
public class MessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);

    @Value("${circuitbreaker.failureRateThreshold:50}")
    private float failureThreshold;

    @Value("${circuitbreaker.waitDurationInOpenState:5}")
    private long waitDurationInOpenState;

    @Value("${circuitbreaker.slidingWindowSize:1}")
    private int slidingWindowSize;

    @Value("${circuitbreaker.minimumNumberOfCalls:1}")
    private int minimumCalls;

    @Autowired
    private JmsTemplate jmsTemplate;

    private CircuitBreaker circuitBreaker;

    @Autowired
    private FailedJmsMessageRepository failedRepo;


    @PostConstruct
    public void init() {
        // Validate and default the failure threshold to avoid exception
        float validatedFailureThreshold = (failureThreshold <= 0.0f || failureThreshold >= 100.0f) ? 50.0f : failureThreshold;

        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(validatedFailureThreshold)
                .waitDurationInOpenState(Duration.ofSeconds(waitDurationInOpenState))
                .slidingWindowSize(slidingWindowSize)
                .minimumNumberOfCalls(minimumCalls)
                .build();

        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);
        this.circuitBreaker = registry.circuitBreaker("messageService");

        LOGGER.info("Initialized circuit breaker with failureRateThreshold={} waitDuration={} slidingWindowSize={} minimumCalls={}",
                validatedFailureThreshold, waitDurationInOpenState, slidingWindowSize, minimumCalls);
    }

    public void send(String queueName, String messageText) {
        sendMessage(queueName, messageText, null);
    }

    public void send(String queueName, String messageText, String groupId) {
        sendMessage(queueName, messageText, groupId);
    }

    private void sendMessage(String queueName, String messageText, String groupId) {
        circuitBreaker.executeRunnable(() -> {
            try {
                jmsTemplate.send(queueName, session -> {
                    TextMessage message = session.createTextMessage(messageText);
                    if (groupId != null) {
                        message.setStringProperty("JMSXGroupID", groupId);
                    }
                    return message;
                });
                LOGGER.info("Message sent to Artemis queue: {}", queueName);
            } catch (Exception e) {
                    LOGGER.error("Failed to send message to Artemis: {}", e.getMessage());

                    FailedJmsMessage failedMsg = new FailedJmsMessage();
                    failedMsg.setQueueName(queueName);
                    failedMsg.setMessage(messageText);
                    failedMsg.setGroupId(groupId);
                    failedMsg.setFailedAt(LocalDateTime.now());

                    failedRepo.save(failedMsg);
                }
        });
    }
}
