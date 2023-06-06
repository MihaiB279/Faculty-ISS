package com.example.employeemanagement.Model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

public class TaskRepository extends Observable{
    static SessionFactory sessionFactory;
    public TaskRepository(){
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
    public Task saveTask(Task task){
        task.setStatus(TaskStatus.IN_WORK);
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(task);
                tx.commit();
                notifyObservers();
                return null;
            } catch (RuntimeException ex) {
                System.err.println("Erorr at at saving worker " + task.getName());
                if (tx != null)
                    tx.rollback();
                return task;
            }
        }
    }

    public boolean deleteTask(String name){
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Task task = session.get(Task.class, name);
                if (task != null) {
                    session.delete(task);
                    tx.commit();
                    notifyObservers();
                    return true;
                } else {
                    System.err.println("Task with name " + name + " not found.");
                    return false;
                }
            } catch (RuntimeException ex) {
                System.err.println("Error occurred while deleting task with name " + name);
                if (tx != null)
                    tx.rollback();
                return false;
            }
        }
    }

    public String updateStatusTask(String name){
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Query query = session.createSQLQuery("UPDATE task SET status = :status where name = :name");
                query.setParameter("status", TaskStatus.FINISHED.toString());
                query.setParameter("name", name);
                int result = query.executeUpdate();

                if (result > 0) {
                    tx.commit();
                    System.out.println("Task status updated successfully.");
                    return null;
                } else {
                    System.err.println("Task not found with name: " + name);
                    return name;
                }
            } catch (RuntimeException ex) {
                System.err.println("Error while selecting task with name: " + name);
                if (tx != null) tx.rollback();
                return name;
            }
        }
    }

    public Iterable<Task> getById(String username) {
        try (Session session = sessionFactory.openSession()) {
            try {
                Query<Task> query = session.createQuery("FROM Task WHERE worker = :username", Task.class);
                query.setParameter("username", username);
                return query.list();
            } catch (RuntimeException ex) {
                System.err.println("Error occurred while retrieving tasks for username: " + username);
                return null;
            }
        }
    }
}
