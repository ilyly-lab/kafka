package com.example.demo.services;

import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Task saveTask(Task task) {

        List<Task> task1 = task.getDependencies();
        List<Task> tasks = task.getSubTask();

        taskRepository.saveAll(task1);
        taskRepository.saveAll(tasks);

        return taskRepository.save(task);
    }
    @Transactional
    public List<Task> getAllTask() {

        return taskRepository.findAllTasksWithAttachmentsAndTasks();
    }
}
