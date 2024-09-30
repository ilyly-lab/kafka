package com.example.demo.controller;

import com.example.annotations.LogExecution;
import com.example.demo.entity.Status;
import com.example.demo.entity.Task;
import com.example.demo.repository.TaskRepository;
import com.example.demo.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class ProductController {

    private final TaskService taskService;


    @Autowired
    public ProductController(TaskService taskService) {
        this.taskService = taskService;

    }

    @PostMapping
    public Task addTask(@RequestBody Task task) {
        return taskService.saveTask(task);
    }

    @GetMapping
    //@LogExecution
    public List<Task> getAllTask() {
        return taskService.getAllTask();
    }

    @GetMapping("/status")
    //@LogExecution
    public Status getStatus(Long l) {
        return taskService.getStatus(l);
    }

    @PostMapping("/updateStatus")
    public void updateTaskStatus(@RequestParam Long taskId, @RequestParam Status status) {
        taskService.updateTaskStatus(taskId, status);
    }

}
