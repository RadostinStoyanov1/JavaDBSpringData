package p12springdataintrobookshop.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import p12springdataintrobookshop.data.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
