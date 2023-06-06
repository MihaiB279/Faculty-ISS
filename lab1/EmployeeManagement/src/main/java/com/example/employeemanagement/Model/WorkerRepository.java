package com.example.employeemanagement.Model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

public class WorkerRepository extends Observable{
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
                query.setParameter("loginHour", worker.getLoginHour());
                query.setParameter("workerStatus", worker.getWorkerStatus().toString());
                query.setParameter("username", worker.getUsername());
                int result = query.executeUpdate();

                if (result > 0) {
                    tx.commit();
                    System.out.println("Login time updated successfully.");
                    notifyObservers();
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
                query.setParameter("logoutHour", worker.getLogoutHour());
                query.setParameter("workerStatus", worker.getWorkerStatus().toString());
                query.setParameter("username", worker.getUsername());
                int result = query.executeUpdate();

                if (result > 0) {
                    tx.commit();
                    System.out.println("Logout time updated successfully.");
                    notifyObservers();
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

    public Worker addWorker(Worker worker){
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(worker);
                tx.commit();
                return null;
            } catch (RuntimeException ex) {
                System.err.println("Erorr at at saving worker " + worker.getName());
                if (tx != null)
                    tx.rollback();
                return worker;
            }
        }
    }

    public boolean deleteWorker(String username) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Employee employee = session.get(Employee.class, username);
                if (employee != null) {
                    session.delete(employee);
                    tx.commit();
                    return true;
                } else {
                    System.err.println("Worker with username " + username + " not found.");
                    return false;
                }
            } catch (RuntimeException ex) {
                System.err.println("Error occurred while deleting worker with username " + username);
                if (tx != null)
                    tx.rollback();
                return false;
            }
        }
    }

    public Employee updateWorker(Worker worker) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Employee existingEmployee = session.get(Employee.class, worker.getUsername());
                if (existingEmployee != null) {
                    existingEmployee.setName(worker.getName());
                    existingEmployee.setPassword(worker.getPassword());

                    session.update(existingEmployee);
                    tx.commit();
                    return null;
                } else {
                    System.err.println("Employee with username " + worker.getUsername() + " not found.");
                    return worker;
                }
            } catch (RuntimeException ex) {
                System.err.println("Error occurred while updating employee " + worker.getUsername());
                if (tx != null)
                    tx.rollback();
                return worker;
            }
        }
    }
    public Iterable<Worker> getActiveWorkers() {
        try (Session session = sessionFactory.openSession()) {
            try {
                String hql = "FROM Worker WHERE status = :status";
                Query<Worker> query = session.createQuery(hql, Worker.class);
                query.setParameter("status", WorkerStatus.PRESENT);
                return query.list();
            } catch (RuntimeException ex) {
                System.err.println("Error occurred while retrieving active workers.");
                return null;
            }
        }
    }
}
