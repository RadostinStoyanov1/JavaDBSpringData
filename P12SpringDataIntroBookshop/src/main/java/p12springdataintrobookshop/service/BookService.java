package p12springdataintrobookshop.service;

import p12springdataintrobookshop.data.entities.Book;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface BookService {
    void seedBooks() throws IOException;

    List<String> findAllBookAfterYear2000();

    List<String> findAllByBookByGeorgePowellOrdered();
}
