import entities.Student;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String connectionUrl = "jdbc:mysql://localhost:3306/hibernate_intro";
        Configuration cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory =
                cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery criteria = builder.createQuery();
        Root<Student> r = criteria.from(Student.class);
        criteria.select(r).where(builder.like(r.get("name"),"P%"));
        List<Student> studentList =
                session.createQuery(criteria).getResultList();
        for (Student student : studentList) {
            System.out.println(student.getName());
        }

        session.getTransaction().commit();
        session.close();
    }
}
