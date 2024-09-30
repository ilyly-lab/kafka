package com.example.services;

import com.example.annotations.LogExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

    private static final String TOPIC = "micro2";

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public Producer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @LogExecution
    public void sendMessageMiro2(String message) {
        kafkaTemplate.send(TOPIC, message);
    }

}
