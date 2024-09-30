package com.example.services;

import com.example.annotations.LogExecution;
import com.example.entity.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    private final NewKafkaServices taskService;

    @Autowired
    public Consumer(NewKafkaServices taskService) {
        this.taskService = taskService;
    }

    @LogExecution
    @KafkaListener(topics = "micro2", groupId = "demo")
    public void consumerDemo(String message) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {

            Task task = objectMapper.readValue(message, Task.class);
            taskService.updateTaskStatus(task.getId(), task.getStatus());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка при десериализации сообщения в Task: " + e.getMessage());
        }



    }
}
