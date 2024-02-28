import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class P03ContainsEmployee {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("softuni_jpa");

        EntityManager entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();

        String[] inputName = READER.readLine().split("\\s+");

        List<Employee> resultList = entityManager
                .createQuery("FROM Employee WHERE firstName = :first_name AND lastName = :last_name", Employee.class)
                .setParameter("first_name", inputName[0])
                .setParameter("last_name", inputName[1])
                .getResultList();

        System.out.printf("%s %s ", inputName[0], inputName[1]);
        System.out.println(resultList.size() > 0 ? "Yes" : "No");

        entityManager.getTransaction().commit();
    }
}
