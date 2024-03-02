package lab.inheritance;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lab.composition.Company;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory main = Persistence.createEntityManagerFactory("main");
        EntityManager entityManager = main.createEntityManager();

        entityManager.getTransaction().begin();
        Company owner = new Company("BulgariaAir");

        Vehicle car = new Car("Corsa", BigDecimal.valueOf(10000), "Petrol", 5);
        Vehicle bike = new Bike("BMX", BigDecimal.ONE, "none");
        Vehicle plane = new Plane("Beoing", BigDecimal.TEN, "kerosene", "KLM", 200, owner);
        Vehicle truck = new Truck("Scania", BigDecimal.TEN, "diesel", 20000);

        entityManager.persist(owner);
        entityManager.persist(car);
        entityManager.persist(bike);
        entityManager.persist(plane);
        entityManager.persist(truck);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
