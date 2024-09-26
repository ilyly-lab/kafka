package com.example.demo.services;

import com.example.demo.entity.Status;
import com.example.demo.entity.Task;
import com.example.demo.repository.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final KafkaProducer kafkaProducer;

    @Autowired
    public TaskService(TaskRepository taskRepository, KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
        this.taskRepository = taskRepository;
    }

    @Transactional
    public Task saveTask(Task task) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        List<Task> task1 = task.getDependencies();
        List<Task> tasks = task.getSubTask();

        taskRepository.saveAll(task1);
        taskRepository.saveAll(tasks);

        task.setStatus(Status.CREATED);

        Task task2 = taskRepository.save(task);

        try {

            String message = objectMapper.writeValueAsString(task2);

            kafkaProducer.sendMessage(message);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка при сериализации Task в JSON: " + e.getMessage());
        }



        return task2;
    }
    @Transactional
    public List<Task> getAllTask() {

        return taskRepository.findAllTasksWithAttachmentsAndTasks();
    }

    @Transactional
    public void updateTaskStatus(Long taskId, Status status) {
        taskRepository.updateTaskStatus(taskId, status);
    }

    @Transactional
    public Status getStatus(Long l) {
        return taskRepository.getStatusTaskForId(l);
    }
}
