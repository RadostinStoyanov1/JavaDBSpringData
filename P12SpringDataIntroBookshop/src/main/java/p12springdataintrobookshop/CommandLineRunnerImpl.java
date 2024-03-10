package p12springdataintrobookshop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import p12springdataintrobookshop.service.AuthorService;
import p12springdataintrobookshop.service.BookService;
import p12springdataintrobookshop.service.CategoryService;

import java.io.IOException;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;

    public CommandLineRunnerImpl(AuthorService authorService, CategoryService categoryService, BookService bookService) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
        printAllBookTitlesAfter2000Year();
        printAllAuthorNamesWithBookBefore1990();
        printALlAuthorsOrderedByBooksPublished();
        printAllbooksOfGeorgePowellOrdered();
    }

    private void printAllbooksOfGeorgePowellOrdered() {
        this.bookService.findAllByBookByGeorgePowellOrdered()
                .forEach(System.out::println);
    }

    private void printALlAuthorsOrderedByBooksPublished() {
        this.authorService.findAllByOrderByBooksSizeDesc()
                .forEach(System.out::println);
    }

    private void printAllAuthorNamesWithBookBefore1990() {
        this.authorService.getAllAuthorsFirstAndLastNameForBooksBeforeYear1990()
                .forEach(System.out::println);
    }

    private void printAllBookTitlesAfter2000Year() {
        this.bookService.findAllBookAfterYear2000()
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        this.authorService.seedAuthors();
        this.categoryService.seedCategories();
        this.bookService.seedBooks();
    }
}
