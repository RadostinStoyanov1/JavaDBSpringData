package p12springdataintrobookshop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import p12springdataintrobookshop.data.entities.enums.EditionType;
import p12springdataintrobookshop.data.repositories.BookInfo;
import p12springdataintrobookshop.service.AuthorService;
import p12springdataintrobookshop.service.BookService;
import p12springdataintrobookshop.service.CategoryService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

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
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//        seedData();
//        printAllBookTitlesAfter2000Year();
//        printAllAuthorNamesWithBookBefore1990();
//        printALlAuthorsOrderedByBooksPublished();
//        printAllbooksOfGeorgePowellOrdered();
//        printAllBooksByAgeCategory(reader.readLine());
//        printAllGoldEditionBooksWithLessThan5000Copies();
//        printAllBooksWithPriceNotBetween(5, 40);
//        printAllBooksNotReleasedInYear(reader.readLine());
//        printBookInfoForBooksReleasedBefore(reader.readLine());
//        printAllAuthorsWhoseFirstNameEndsWith(reader.readLine());
//        printAllBooksWHoseTitleContains(reader.readLine());
//        printAllBooksWhoseAuthorsLastNameStartsWith(reader.readLine());
//        printAllBooksCountWithTitleLengthHigherThan(Integer.parseInt(reader.readLine()));
//        printTotalBookCopiesForAuthor(reader.readLine());
//        printBookProjection(reader.readLine());
        printAddedcopiesOfupdatedBooksReleaseAfter();


    }

    private void printAddedcopiesOfupdatedBooksReleaseAfter() {
        Scanner scanner = new Scanner(System.in);
        String dateString = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.US);
        LocalDate releaseDate = LocalDate.parse(dateString, formatter);
        int increaseCopies = Integer.parseInt(scanner.nextLine());

        int increasedBookCount = bookService.upadateBookCopiesReleasedAfter(increaseCopies, releaseDate);
        System.out.println(increaseCopies * increasedBookCount);
    }

    private void printBookProjection(String givenTitle) {
        BookInfo info = bookService.findInfoByTitle(givenTitle);
        String output = String.format("%s %s %s %.2f",
                info.getTitle(),
                info.getEditionType(),
                info.getAgeRestriction(),
                info.getPrice());
        System.out.println(output);
    }

    private void printTotalBookCopiesForAuthor(String authorNames) {
        String firstName = authorNames.split("\\s+")[0];
        String lastName = authorNames.split("\\s+")[1];
        System.out.println(authorService.getTotalCopiesPerAuthorFot(firstName, lastName));
    }

    private void printAllBooksCountWithTitleLengthHigherThan(int minTitleLength) {
        int count = bookService.findTitlesCountLongerThan(minTitleLength);
        System.out.println(count);
    }

    private void printAllBooksWhoseAuthorsLastNameStartsWith(String input) {
        bookService.findAllBookWhoseAuthorLastNameStartsWith(input)
                .forEach(System.out::println);
    }

    private void printAllBooksWHoseTitleContains(String s) {
        bookService.findAllBooksWhoseTitleContains(s)
                .forEach(System.out::println);
    }

    private void printAllAuthorsWhoseFirstNameEndsWith(String part) {
        authorService.findAllAuthorsWhoseFirstNameEndsWith(part)
                .forEach(System.out::println);
    }

    private void printBookInfoForBooksReleasedBefore(String input) {
        LocalDate date = LocalDate.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        bookService.findAllBooksReleasedBeforeDate(date)
                .forEach(System.out::println);
    }

    private void printAllBooksNotReleasedInYear(String s) {
        this.bookService.findAllBooksNotReleasedInGivenYear(Integer.parseInt(s))
                .forEach(System.out::println);
    }

    private void printAllBooksWithPriceNotBetween(double low, double high) {
        this.bookService.findAllBooksPriceNotBetween(low, high)
                .forEach(System.out::println);
    }

    private void printAllGoldEditionBooksWithLessThan5000Copies() {
        this.bookService.findAllBooksByEditionAndCopies(EditionType.GOLD, 5000)
                .forEach(System.out::println);
    }

    private void printAllBooksByAgeCategory(String s) {
        this.bookService.findAllBooksByAgeRestriction(s)
                .forEach(System.out::println);
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
