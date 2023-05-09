package com.example.employeemanagement.Model;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "employee")
public class Employee {
    @Column(name = "name")
    private String name;
    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "isboss")
    private Boolean isboss;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsBoss() {
        return isboss;
    }

    public void setIsBoss(Boolean isboss) {
        this.isboss = isboss;
    }

    public Employee(){}
    public Employee(String name, String username, String password, Boolean isboss) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.isboss = isboss;
    }
}
