package com.example.employeemanagement.Model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "boss")
public class Boss extends Employee{
    public Boss(String name, String username, String password, Boolean isBoss) {
        super(name, username, password, isBoss);
    }

    public Boss() {
        super();
    }
}
