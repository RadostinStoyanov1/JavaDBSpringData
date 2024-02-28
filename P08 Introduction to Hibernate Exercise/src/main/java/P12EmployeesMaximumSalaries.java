import entities.Department;
import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;

public class P12EmployeesMaximumSalaries {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("softuni_jpa");
        EntityManager entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();
        Map<String, Double> departmentsMax = new LinkedHashMap<>();

        List<Department> departmentsList = entityManager.createQuery("FROM Department", Department.class)
                .getResultList();

        for (Department department : departmentsList) {
            Set<Employee> employeesSet = department.getEmployees();
            Double maxSalary = employeesSet.stream().mapToDouble(e -> e.getSalary().doubleValue()).max().orElse(0);
            if (maxSalary < 30000 || maxSalary > 70000) {
                departmentsMax.put(department.getName(), maxSalary);
            }
        }

        departmentsMax.forEach((key, value) -> System.out.printf("%s %.2f%n", key, value));

        entityManager.getTransaction().commit();
    }
}
