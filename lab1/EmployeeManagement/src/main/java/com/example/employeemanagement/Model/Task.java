package com.example.employeemanagement.Model;

import javax.persistence.*;

@Entity
@Table(name = "task")
public class Task {
    @Id
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    private String worker;
    public Task(){}

    public Task(String name, String description, String worker) {
        this.name = name;
        this.description = description;
        this.worker = worker;
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

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }
}
