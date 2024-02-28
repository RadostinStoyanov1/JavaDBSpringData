import entities.Address;
import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Comparator;

public class P09FindLatest10Projects {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("softuni_jpa");
        EntityManager entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();

        entityManager.createQuery("FROM Project ORDER BY startDate DESC", Project.class)
                .setMaxResults(10)
                .getResultStream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(project -> {
                    System.out.printf("Project name: %s%n", project.getName());
                    System.out.printf("     Project Description: %s%n", project.getDescription());
                    System.out.printf("     Project Start Date:%s%n", project.getStartDate());
                    System.out.printf("     Project End Date: %s%n", project.getEndDate());
                });

        entityManager.getTransaction().commit();
    }
}
