package com.example.demo.services;

import com.example.annotations.LogExecution;
import com.example.demo.entity.Task;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;



@Service
public class KafkaProducer {

    private static final String TOPIC = "tasks";

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @LogExecution
    public void sendMessage(String message) {
        kafkaTemplate.send(TOPIC, message);
        //System.out.println("Сообщение отправлено в Kafka: ");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            Task task = objectMapper.readValue(message, Task.class);
            System.out.println(task);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка при десериализации сообщения в Task: " + e.getMessage());
        }

    }
}
