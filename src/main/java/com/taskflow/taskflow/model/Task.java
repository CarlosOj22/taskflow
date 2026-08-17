package com.taskflow.taskflow.model;


import java.time.LocalDateTime;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Task {

    //Better Long for JPA/Hibernete nulls, Long is an objetc (wrapper)
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    //Enumerad tell JPA not to use smallint for enums.
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    //Constructor
    public Task(String name,String description,TaskStatus status,TaskPriority priority){
        this.name=name;
        this.description=description;
        this.status=status;
        this.priority=priority;
    }

    public Task(){
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }
}
