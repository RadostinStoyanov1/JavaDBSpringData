import entities.Address;
import entities.Employee;
import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;

public class P08GetEmployeesWithProject {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("softuni_jpa");
        EntityManager entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();

        Employee resultEmployee = entityManager.createQuery("FROM Employee e WHERE e.id = ?1", Employee.class)
                .setParameter(1, Integer.parseInt(READER.readLine()))
                .getSingleResult();

        System.out.printf("%s %s - %s%n", resultEmployee.getFirstName(), resultEmployee.getLastName(), resultEmployee.getJobTitle());
        resultEmployee.getProjects().stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(project -> System.out.printf(" %s%n", project.getName()));

        entityManager.getTransaction().commit();
    }
}
