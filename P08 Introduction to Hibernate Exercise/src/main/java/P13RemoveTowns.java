import entities.Address;
import entities.Department;
import entities.Employee;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class P13RemoveTowns {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("softuni_jpa");
        EntityManager entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();

        String townName = READER.readLine();

        List<Town> townsList = entityManager.createQuery("FROM Town t WHERE t.name = :inputTown", Town.class)
                .setParameter("inputTown", townName)
                .getResultList();

        if (townsList.isEmpty() || townsList.get(0) == null) {
            System.out.printf("Town: %s not found", townName);
            return;
        }

        List<Address> foundAddresses = entityManager.createQuery("FROM Address", Address.class)
                .getResultStream()
                .filter(address -> address.getTown().getName().equals(townName))
                .toList();

        foundAddresses.forEach(address -> {
            address.getEmployees().forEach(e -> {
                e.setAddress(null);
                entityManager.persist(e);
            });
            entityManager.remove(address);
        });

        System.out.printf("%d address in %s deleted", foundAddresses.size(), townName);

        entityManager.remove(townsList.get(0));

        entityManager.getTransaction().commit();
    }
}
