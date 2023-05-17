package com.example.employeemanagement.Model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

public class WorkerRepository {
    static SessionFactory sessionFactory;

    public WorkerRepository() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Exceptie " + e);
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public Employee updateLoginTime(Worker worker) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Query query = session.createSQLQuery("UPDATE worker SET loginhour = :loginHour, logouthour = DEFAULT, status = :workerStatus WHERE username = :username");
                query.setParameter("loginHour", worker.getLogInTime());
                query.setParameter("workerStatus", worker.getWorkerStatus().toString());
                query.setParameter("username", worker.getUsername());
                int result = query.executeUpdate();

                if (result > 0) {
                    tx.commit();
                    System.out.println("Logout time updated successfully.");
                    return null;
                } else {
                    System.err.println("Worker not found with username: " + worker.getUsername());
                    return worker;
                }
            } catch (RuntimeException ex) {
                System.err.println("Error while selecting worker with username: " + worker.getUsername());
                if (tx != null) tx.rollback();
                return worker;
            }
        }
    }

    public Employee updateLogoutTime(Worker worker) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Query query = session.createSQLQuery("UPDATE worker SET logouthour = :logoutHour, status = :workerStatus WHERE username = :username");
                query.setParameter("logoutHour", worker.getLogOutTime());
                query.setParameter("workerStatus", worker.getWorkerStatus().toString());
                query.setParameter("username", worker.getUsername());
                int result = query.executeUpdate();

                if (result > 0) {
                    tx.commit();
                    System.out.println("Logout time updated successfully.");
                    return null;
                } else {
                    System.err.println("Worker not found with username: " + worker.getUsername());
                    return worker;
                }
            } catch (RuntimeException ex) {
                System.err.println("Error while selecting worker with username: " + worker.getUsername());
                if (tx != null) tx.rollback();
                return worker;
            }
        }
    }
}
