package entities;

import entities.P03UniversitySystem.Student;
import entities.P03UniversitySystem.Teacher;
import entities.P05BillsPaymentSystem.CardType;
import entities.P05BillsPaymentSystem.CreditCard;
import entities.P05BillsPaymentSystem.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("main");
        EntityManager entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();

        entityManager.getTransaction().commit();

    }
}
