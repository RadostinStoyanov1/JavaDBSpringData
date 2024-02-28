import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class P05EmployeesFromDepartment {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("softuni_jpa");
        EntityManager entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();

        List<Employee> resultList = entityManager.createQuery("SELECT e FROM Employee e JOIN e.department d WHERE d.name = 'Research and Development' ORDER BY e.salary, e.id", Employee.class).getResultList();

        resultList.stream().forEach(e -> {
            System.out.printf("%s %s from Research and Development - $%.2f\n",
                    e.getFirstName(),
                    e.getLastName(),
                    e.getSalary());
        });

        entityManager.getTransaction().commit();
    }
}
