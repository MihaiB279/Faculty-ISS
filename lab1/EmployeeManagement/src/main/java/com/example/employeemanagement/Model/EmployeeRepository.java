package com.example.employeemanagement.Model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

public class EmployeeRepository {
    static SessionFactory sessionFactory;

    public EmployeeRepository() {
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

    public Employee findEmployee(String username, String password) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Query<Employee> query = session.createQuery("FROM Employee AS e WHERE e.username = :username and e.password = :password", Employee.class);
                query.setParameter("username", username);
                query.setParameter("password", password);
                List<Employee> agencyList = query.getResultList();
                if (!agencyList.isEmpty()) {
                    Employee employee = agencyList.get(0);
                    tx.commit();
                    return employee;
                } else {
                    System.err.println("Employee not found with username: " + username + "; and password" + password);
                    return null;
                }
            } catch (RuntimeException ex) {
                System.err.println("Error while selecting Employee with username: " + username + "; and password" + password);
                if (tx != null) tx.rollback();
                return null;
            }
        }
    }
}
