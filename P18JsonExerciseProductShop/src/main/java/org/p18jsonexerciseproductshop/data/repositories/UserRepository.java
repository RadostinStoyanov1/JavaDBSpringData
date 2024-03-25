package org.p18jsonexerciseproductshop.data.repositories;

import org.p18jsonexerciseproductshop.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u JOIN Product p ON p.seller = u WHERE p.buyer IS NOT NULL ORDER BY SIZE(u.productsSold) DESC, u.lastname ")
    List<User> getAllUsersWithSoldProducts();


}
