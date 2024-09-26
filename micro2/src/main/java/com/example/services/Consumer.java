package com.example.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    @KafkaListener(topics = "tasks", groupId = "micro")
    public void consumerMicro(String message) {
        System.out.println("Сообщение прослушано в micro: " + message);
    }

}
