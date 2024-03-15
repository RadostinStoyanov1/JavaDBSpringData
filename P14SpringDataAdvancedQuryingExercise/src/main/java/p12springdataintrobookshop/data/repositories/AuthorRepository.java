package p12springdataintrobookshop.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import p12springdataintrobookshop.data.entities.Author;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Set<Author> findAllByBooksReleaseDateBefore(LocalDate date);

    @Query("FROM Author a ORDER BY SIZE(a.books) DESC")
    Set<Author> findAllByOrderByBooksSizeDesc();

    Set<Author> findAllByFirstNameEndsWith(String part);

    @Query("SELECT SUM(b.copies) FROM Book b JOIN b.author a WHERE a.firstName = :firstName AND a.lastName = :lastName")
    int countBookCopiesByFirstNameAndLastName(String firstName, String lastName);

}
