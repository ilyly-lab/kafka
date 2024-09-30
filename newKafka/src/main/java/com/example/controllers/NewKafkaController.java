package com.example.controllers;

import com.example.services.NewKafkaServices;
import com.example.entity.Status;
import com.example.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/task")
public class NewKafkaController {

    @Autowired
    public NewKafkaController(NewKafkaServices newKafkaServices) {
        this.newKafkaServices = newKafkaServices;
    }

    private final NewKafkaServices newKafkaServices;



    @PostMapping("/save")
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        return ResponseEntity.ok(newKafkaServices.saveTask(task));
    }

    @GetMapping("/getAllTasks")
    public List<Task> getAllTask() {
        return newKafkaServices.getAllTask();
    }
    @GetMapping("/getStatusForId")
    public Status getStatus(Long id) {
        return newKafkaServices.getStatusForId(id);
    }
    @PostMapping("/updateStatus")
    public void updateTaskStatus(@RequestParam Long taskId, @RequestParam Status status) {
        newKafkaServices.updateTaskStatus(taskId, status);
    }
}
