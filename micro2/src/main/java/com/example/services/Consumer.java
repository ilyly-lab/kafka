package com.example.services;

import com.example.annotations.LogExecution;
import com.example.dto.TaskDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.Random;

@Service
public class Consumer {

    private final Producer producer;
    Random random = new Random();

    @Autowired
    public Consumer(Producer producer) {
        this.producer = producer;
    }

    @LogExecution
    @KafkaListener(topics = "tasks", groupId = "micro")
    public void consumerMicro(String message) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());


            try {

                Thread.sleep(1000);

                TaskDto taskDto = objectMapper.readValue(message, TaskDto.class);

                TaskDto taskDtoNewStatus = updateTaskStatus(taskDto);

                String messageUpdateStatus = objectMapper.writeValueAsString(taskDtoNewStatus);

                producer.sendMessageMiro2(messageUpdateStatus);

            } catch (Exception e) {

                e.printStackTrace();

                System.out.println("Ошибка при десериализации сообщения в Task: " + e.getMessage());

            }
    }

    private TaskDto updateTaskStatus(TaskDto taskDto) {
        taskDto.setStatus(random.nextInt(10) == 0 ? "ERROR" : "DONE");
        return taskDto;
    }

}
