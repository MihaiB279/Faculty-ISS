package com.example.employeemanagement.Model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "worker")
public class Worker extends Employee{
    public Worker(String name, String username, String password, Boolean isBoss) {
        super(name, username, password, isBoss);
    }

    public Worker() {
        super();
    }
}
