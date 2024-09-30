package com.example.services;

import com.example.annotations.LogExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class KafkaProducer {

    private static final String TOPIC = "tasks";

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @LogExecution
    public void sendMessageFromNewKafka(String message) {

        kafkaTemplate.send(TOPIC, message);


    }
}
