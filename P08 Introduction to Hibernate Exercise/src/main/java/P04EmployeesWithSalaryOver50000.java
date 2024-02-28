import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class P04EmployeesWithSalaryOver50000 {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("softuni_jpa");
        EntityManager entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();

        List<Employee> employees = entityManager.createQuery("FROM Employee WHERE salary > 50000", Employee.class)
                        .getResultList();

        employees.stream().forEach(employee -> {
            System.out.println(employee.getFirstName());
        });

        entityManager.getTransaction().commit();
    }
}
