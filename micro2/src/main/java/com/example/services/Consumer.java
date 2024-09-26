package com.example.services;

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

    @KafkaListener(topics = "tasks", groupId = "micro")
    public void consumerMicro(String message) {
        System.out.println("Сообщение прослушано в micro: ");

        try {
            Thread.sleep(1000);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            try {

                TaskDto taskDto = objectMapper.readValue(message, TaskDto.class);
                System.out.println("После десериализации и мфпинга в дто: " + taskDto);

                TaskDto taskDtoNewStatus = updateTaskStatus(taskDto);
                System.out.println("статус сообщения изменен на рандом: " + taskDto.getStatus());

                String messageUpdateStatus = objectMapper.writeValueAsString(taskDtoNewStatus);

                System.out.println("сообщение с обновленным статусом: " + messageUpdateStatus);

                producer.sendMessage(message);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Ошибка при десериализации сообщения в Task: " + e.getMessage());
            }



        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private TaskDto updateTaskStatus(TaskDto taskDto) {
        taskDto.setStatus(random.nextInt(10) == 0 ? "ERROR" : "DONE");
        return taskDto;
    }

}
