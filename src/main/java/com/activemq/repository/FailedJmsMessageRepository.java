package com.activemq.repository;

import com.activemq.entity.FailedJmsMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FailedJmsMessageRepository extends JpaRepository<FailedJmsMessage, Long> {
}
