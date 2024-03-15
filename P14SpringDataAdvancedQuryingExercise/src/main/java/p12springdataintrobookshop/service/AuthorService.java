package p12springdataintrobookshop.service;

import p12springdataintrobookshop.data.entities.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {
    void seedAuthors() throws IOException;

    Author getRandomAuthor();
    List<String> getAllAuthorsFirstAndLastNameForBooksBeforeYear1990();

    List<String> findAllByOrderByBooksSizeDesc();

    List<String> findAllAuthorsWhoseFirstNameEndsWith(String part);

    int getTotalCopiesPerAuthorFot(String firstName, String lastName);
}
