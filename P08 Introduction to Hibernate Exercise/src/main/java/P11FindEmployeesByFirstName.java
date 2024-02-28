import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;

public class P11FindEmployeesByFirstName {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("softuni_jpa");
        EntityManager entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();

        entityManager.createQuery("FROM Employee e WHERE e.firstName LIKE CONCAT(:inputPattern, '%')", Employee.class)
                        .setParameter("inputPattern", READER.readLine())
                        .getResultStream()
                        .forEach(employee -> System.out.printf("%s %s - %s - ($%.2f)%n",
                                employee.getFirstName(),
                                employee.getLastName(),
                                employee.getJobTitle(),
                                employee.getSalary()));

        entityManager.getTransaction().commit();
    }
}
