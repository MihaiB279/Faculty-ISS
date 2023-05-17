package com.example.employeemanagement.Model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "worker")
public class Worker extends Employee {
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private WorkerStatus status;

    public WorkerStatus getWorkerStatus() {
        return status;
    }
    @Column(name = "loginhour")
    private LocalDateTime loginHour;
    @Column(name = "logouthour")
    private LocalDateTime logoutHour;

    public Worker(String name, String username, String password, Boolean isBoss) {
        super(name, username, password, isBoss);
    }

    public Worker() {
        super();
    }

    public void setLogInTime(LocalDateTime time) {
        loginHour = time;
        status = WorkerStatus.PRESENT;
    }
    public void setLogOutTime(LocalDateTime time) {
        logoutHour = time;
        status = WorkerStatus.ABSENT;
    }
    public LocalDateTime getLogInTime() {
        return loginHour;
    }
    public LocalDateTime getLogOutTime() {
        return logoutHour;
    }

}
