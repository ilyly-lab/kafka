package com.example.demo.services;

import com.example.annotations.LogExecution;
import com.example.demo.entity.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class Consumer {

    private final TaskService taskService;

    @Autowired
    public Consumer(TaskService taskService) {
        this.taskService = taskService;
    }

    @LogExecution
    @KafkaListener(topics = "micro2", groupId = "demo")
    public void consumerDemo(String message) {
        //System.out.println("Сообщение считал consumer demo: " + message);

        // Создаем ObjectMapper для преобразования строки в объект Task
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            // Преобразуем строку JSON в объект Task
            Task task = objectMapper.readValue(message, Task.class);
            System.out.println("----====  " + task.getId() + "==== " + task.getStatus());
            taskService.updateTaskStatus(task.getId(), task.getStatus()); // Обновляем статус задачи

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка при десериализации сообщения в Task: " + e.getMessage());
        }



    }
}
