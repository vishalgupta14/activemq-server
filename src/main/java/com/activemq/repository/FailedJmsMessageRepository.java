package com.activemq.repository;

import com.activemq.entities.FailedJmsMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FailedJmsMessageRepository extends JpaRepository<FailedJmsMessage, Long> {

    List<FailedJmsMessage> findTop10ByOrderByFailedAtAsc();

}
