package org.translocathor.demo;

import org.translocathor.demo.model.Company;
import org.translocathor.demo.model.Employee;

import javax.persistence.*;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        // Setup the entity manager
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("example");
        EntityManager em = factory.createEntityManager();

        //Create a company
        Company company = new Company();
        company.setName(String.format("Company %s", new Random().nextInt(100)));

        Employee e1 = new Employee("Mike", "Stevens", 50000);
        Employee e2 = new Employee("Rachel", "Hadden", 65000);

        company.getEmployees().add(e1);
        company.getEmployees().add(e2);

        EntityTransaction trans = em.getTransaction();
        System.out.println(String.format("Persisting company %s with %s employees", company.getName(), company.getEmployees().size()));
        trans.begin();
        em.persist(company);
        trans.commit();

        TypedQuery<Company> q = em.createQuery("select c from Company c", Company.class);
        List<Company> companyList = q.getResultList();
        for (Company currentCompany : companyList) {
            System.out.println(String.format("Loaded company %s from database", currentCompany.getName()));
        }

        TypedQuery<Employee> q2 = em.createQuery("select e from Employee e", Employee.class);
        List<Employee> results = q2.getResultList();
        System.out.println(String.format("Database now contains %s employees in total", results.size()));

        em.close();
        factory.close();
    }
}
