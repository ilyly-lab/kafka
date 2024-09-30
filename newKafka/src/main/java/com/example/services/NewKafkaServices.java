package com.example.services;

import com.example.repository.NewKafkaRepository;
import com.example.annotations.LogExecution;
import com.example.entity.Status;
import com.example.entity.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NewKafkaServices {

    private final KafkaProducer kafkaProducer;

    @Autowired
    public NewKafkaServices(NewKafkaRepository newKafkaRepository, KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
        this.newKafkaRepository = newKafkaRepository;
    }

    public final NewKafkaRepository newKafkaRepository;

    @Transactional
    @LogExecution
    public Task saveTask(Task task) {

        String message = "";

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        List<Task> task1 = task.getDependencies();
        List<Task> tasks = task.getSubTask();

        newKafkaRepository.saveAll(task1);
        newKafkaRepository.saveAll(tasks);

        task.setStatus(Status.CREATED);

        Task task2 = newKafkaRepository.save(task);

        try {

            message = objectMapper.writeValueAsString(task2);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка при сериализации Task в JSON: " + e.getMessage());
        }
        kafkaProducer.sendMessageFromNewKafka(message);
        return task2;
    }

    @Transactional
    @LogExecution
    public List<Task> getAllTask() {
        return newKafkaRepository.findAllTasksWithAttachmentsAndTasks();
    }

    @Transactional
    @LogExecution
    public Status getStatusForId(Long l) {
        return newKafkaRepository.getStatusTaskForId(l);
    }

    @LogExecution
    @Transactional
    public void updateTaskStatus(Long taskId, Status status) {
        newKafkaRepository.updateTaskStatus(taskId, status);
    }

}
