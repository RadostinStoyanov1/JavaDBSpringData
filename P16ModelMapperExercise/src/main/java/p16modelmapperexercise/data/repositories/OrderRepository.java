package p16modelmapperexercise.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import p16modelmapperexercise.data.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
