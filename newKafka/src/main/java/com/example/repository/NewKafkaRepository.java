package com.example.repository;

import com.example.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.entity.Status;

import java.util.List;

@Repository
public interface NewKafkaRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.attachments a LEFT JOIN FETCH a.tasks")
    List<Task> findAllTasksWithAttachmentsAndTasks();

    @Query("SELECT t.status FROM Task t WHERE t.id = :taskId")
    Status getStatusTaskForId(@Param("taskId") Long taskId);

    @Modifying
    @Query("UPDATE Task t SET t.status = :status WHERE t.id = :taskId")
    void updateTaskStatus(@Param("taskId") Long taskId, @Param("status") Status status);
}

