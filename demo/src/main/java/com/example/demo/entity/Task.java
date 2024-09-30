package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("name")
    private String name;
    private String description;//описание
    private Priority priority;
    @JsonProperty("status")
    private Status status;
    private LocalDateTime dueDate;//дата завершения
    private LocalDateTime createdDate;//дата создания
    private LocalDateTime updateDate;//дата последнего обновления
    private Integer estimatedTimeMinutes;
    private Integer progressPercentage;//прогресс в процентах

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "task_files",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id")
    )
    private Set<File> attachments;//прикрепленные\вложенные файлы скачиваются по этим путям

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "assignee_id")
    @JsonProperty("assignee")
    private User assignee;//ответственный за выполнение

    @ElementCollection
    @CollectionTable(name = "task_tags", joinColumns = @JoinColumn(name = "task_id"))
    @Column(name = "tag")
    private Set<String> tags;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private List<Comment> comments;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "task_dependencies",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "dependency_id")
    )
    private List<Task> dependencies = new ArrayList<>();//список задач которые должны быть выполнены до начала этой задачи

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "task_subtasks",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "subtask_id")
    )
    @JsonProperty("subTask")
    private List<Task> subTask = new ArrayList<>();//подзадачи связанные с основной подзадачей


    public Task(String name, String description, Priority priority,
                Status status, LocalDateTime dueDate, LocalDateTime createdDate,
                LocalDateTime updateDate, User assignee, Set<String> tags,
                List<Comment> comments, Integer estimatedTimeMinutes,
                Set<File> attachments, Integer progressPercentage, List<Task> dependencies, List<Task> subTask) {
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.dueDate = dueDate;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
        this.assignee = assignee;
        this.tags = tags;
        this.comments = comments;
        this.estimatedTimeMinutes = estimatedTimeMinutes;
        this.attachments = attachments;
        this.progressPercentage = progressPercentage;
        this.dependencies = dependencies;
        this.subTask = subTask;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Task(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Integer getEstimatedTimeMinutes() {
        return estimatedTimeMinutes;
    }

    public void setEstimatedTimeMinutes(Integer estimatedTimeMinutes) {
        this.estimatedTimeMinutes = estimatedTimeMinutes;
    }

    public Set<File> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<File> attachments) {
        this.attachments = attachments;
    }

    public Integer getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(Integer progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public List<Task> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<Task> dependencies) {
        this.dependencies = dependencies;
    }

    public List<Task> getSubTask() {
        return subTask;
    }

    public void setSubTask(List<Task> subTask) {
        this.subTask = subTask;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", status=" + status +
                ", dueDate=" + dueDate +
                ", createdDate=" + createdDate +
                ", updateDate=" + updateDate +
                ", estimatedTimeMinutes=" + estimatedTimeMinutes +
                ", progressPercentage=" + progressPercentage +
                ", attachments=" + attachments +
                ", assignee=" + assignee +
                ", tags=" + tags +
                ", comments=" + comments +
                ", dependencies=" + dependencies +
                ", subTask=" + subTask +
                '}';
    }
}
