package lab.composition;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lab.inheritance.*;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory main = Persistence.createEntityManagerFactory("main");
        EntityManager entityManager = main.createEntityManager();

        entityManager.getTransaction().begin();

        PlateNumber number = new PlateNumber("P7237BP");
        CompositionCar car = new CompositionCar("Corsa", BigDecimal.valueOf(10000), "Petrol", 5, number);
        entityManager.persist(number);
        entityManager.persist(car);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
