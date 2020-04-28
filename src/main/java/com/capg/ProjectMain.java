package com.capg;


import javax.persistence.*;
import java.util.List;

public class ProjectMain {
    EntityManagerFactory emf;

    public static void main(String[] args) {
        ProjectMain project = new ProjectMain();
        project.execute();
    }

    void execute() {
        //entity manager factory created
        emf = Persistence.createEntityManagerFactory("testConnect");
        Employee employee = createEmployee();
        int id = employee.getId();
        Employee found = findEmployeeById(id);
        print(found);

        found.setName("Amit");
        updateEmployee(found);

        print(found);

        List<Employee>employees=findAllEmployees();

        //removeEmployeeById(id); //uncomment if you want to remove employee
        System.out.println("printing all employees");
        print(employees);
        emf.close();
    }

    void print(Employee employee){
        System.out.println("employee id="+employee.getId()+" name="+employee.getName());

    }
    void print(List<Employee>employees){
        for (Employee employee:employees){
            print(employee);
        }
    }

    Employee findEmployeeById(int id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Employee e = em.find(Employee.class, id);
        transaction.commit();
        em.close();
        return e;
    }

    List<Employee> findAllEmployees(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Query query =em.createQuery("from Employee");
        List<Employee>employees=query.getResultList();
        return employees;
    }

    Employee updateEmployee(Employee employee){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        employee=em.merge(employee);
        transaction.commit();
        em.close();
        return employee;
    }

    void removeEmployeeById(int id){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Employee employee=em.find(Employee.class,id);
        em.remove(employee);
        transaction.commit();
        em.close();
        System.out.println("employee removed with id="+id);
    }


    Employee createEmployee() {

        EntityManager em = emf.createEntityManager();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Employee employee = new Employee();
        employee.setName("shubham kaporr");

        em.persist(employee);

        transaction.commit();

        System.out.println("Add one employee, withd id=" + employee.getId());

        em.close();
        return employee;
    }
}