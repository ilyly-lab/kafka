package com.example.demo.services;

import com.example.demo.entity.Task;
import com.example.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

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

        List<Task> task1 = task.getDependencies();
        List<Task> tasks = task.getSubTask();

        taskRepository.saveAll(task1);
        taskRepository.saveAll(tasks);

        kafkaProducer.sendMessage("....................................................................................");

        return taskRepository.save(task);
    }
    @Transactional
    public List<Task> getAllTask() {

        return taskRepository.findAllTasksWithAttachmentsAndTasks();
    }
}
