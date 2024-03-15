package p12springdataintrobookshop.data.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import p12springdataintrobookshop.data.entities.Author;
import p12springdataintrobookshop.data.entities.Book;
import p12springdataintrobookshop.data.entities.enums.AgeRestriction;
import p12springdataintrobookshop.data.entities.enums.EditionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    Set<Book> findAllByReleaseDateAfter(LocalDate date);

    Set<Book> findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitle(String firstName, String lastName);

    Set<Book> findAllByAgeRestrictionEquals(AgeRestriction ageRestriction);

    Set<Book> findAllByEditionTypeAndCopiesLessThan(EditionType type, int copies);

    Set<Book> findAllByPriceIsLessThanOrPriceGreaterThan(BigDecimal low, BigDecimal high);

    Set<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate dateStart, LocalDate dateEnd);

    Set<Book> findAllByReleaseDateBefore(LocalDate date);

    Set<Book> findAllByTitleContaining(String part);

    Set<Book> findAllByAuthorLastNameStartsWith(String part);

    @Query("SELECT COUNT(b) FROM Book b WHERE LENGTH(b.title) > :min")
    int countByTitleLengthGreaterThan(int min);

    BookInfo findByTitle(String title);

    @Modifying
    @Transactional
    @Query("UPDATE Book b SET b.copies = b.copies + :copiesToAdd WHERE b.releaseDate > :localDate")
    int updateBookCopiesReleasedAfter(int copiesToAdd, LocalDate localDate);
}
