package p12springdataintrobookshop.service;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import p12springdataintrobookshop.data.entities.enums.EditionType;
import p12springdataintrobookshop.data.repositories.BookInfo;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<String> findAllBookAfterYear2000();

    List<String> findAllByBookByGeorgePowellOrdered();

    List<String> findAllBooksByAgeRestriction(String ageRestriction);

    List<String> findAllBooksByEditionAndCopies(EditionType type, int i);

    List<String> findAllBooksPriceNotBetween(double low, double high);

    List<String> findAllBooksNotReleasedInGivenYear(int year);

    List<String> findAllBooksReleasedBeforeDate(LocalDate date);

    List<String> findAllBooksWhoseTitleContains(String part);

    List<String> findAllBookWhoseAuthorLastNameStartsWith(String part);

    int findTitlesCountLongerThan(int minTitleLength);

    BookInfo findInfoByTitle(String givenTitle);

    int upadateBookCopiesReleasedAfter(int copiesToAdd, LocalDate localDate);

}
