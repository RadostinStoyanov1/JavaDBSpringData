package p12springdataintrobookshop.service.Impl;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.springframework.stereotype.Service;
import p12springdataintrobookshop.data.entities.Author;
import p12springdataintrobookshop.data.entities.Book;
import p12springdataintrobookshop.data.entities.Category;
import p12springdataintrobookshop.data.entities.enums.AgeRestriction;
import p12springdataintrobookshop.data.entities.enums.EditionType;
import p12springdataintrobookshop.data.repositories.BookInfo;
import p12springdataintrobookshop.data.repositories.BookRepository;
import p12springdataintrobookshop.service.AuthorService;
import p12springdataintrobookshop.service.BookService;
import p12springdataintrobookshop.service.CategoryService;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static javax.swing.text.html.HTML.Attribute.N;

@Service
public class BookServiceImpl implements BookService {
    private static final String FILE_PATH = "C:\\Rado\\Intellij Tasks Spring Data\\P12SpringDataIntroBookshop\\src\\main\\resources\\files\\books.txt";
    final BookRepository bookRepository;
    final AuthorService authorService;
    final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {
        if (this.bookRepository.count() == 0) {
            Files.readAllLines(Path.of(FILE_PATH))
                    .stream()
                    .filter(row -> !row.isEmpty())
                    .forEach(row -> {
                        String[] data = row.split("\\s+");

                        Author author = this.authorService.getRandomAuthor();
                        EditionType editionType = EditionType.values()[Integer.parseInt(data[0])];
                        LocalDate releaseDate = LocalDate.parse(data[1],
                                DateTimeFormatter.ofPattern("d/M/yyyy"));
                        int copies = Integer.parseInt(data[2]);
                        BigDecimal price = new BigDecimal(data[3]);
                        AgeRestriction ageRestriction = AgeRestriction
                                .values()[Integer.parseInt(data[4])];
                        String title = Arrays.stream(data)
                                .skip(5)
                                .collect(Collectors.joining(" "));
                        Set<Category> categories = this.categoryService.getRandomCategories();


                        Book book = new Book(title, editionType, price, copies, releaseDate,
                                ageRestriction, author, categories);

                        this.bookRepository.saveAndFlush(book);
                    });
        }
    }

    @Override
    public List<String> findAllBookAfterYear2000() {
        return this.bookRepository.findAllByReleaseDateAfter(LocalDate.of(2000, 12, 31))
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllByBookByGeorgePowellOrdered() {
        return this.bookRepository.findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitle("George", "Powell")
                .stream()
                .map(book -> String.format("%s %s %d", book.getTitle(), book.getReleaseDate(), book.getCopies()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByAgeRestriction(String ageRestriction) {
        return this.bookRepository.findAllByAgeRestrictionEquals(AgeRestriction.valueOf(ageRestriction.toUpperCase()))
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByEditionAndCopies(EditionType type, int i) {
        return this.bookRepository.findAllByEditionTypeAndCopiesLessThan(type, 5000)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksPriceNotBetween(double low, double high) {
        return bookRepository.findAllByPriceIsLessThanOrPriceGreaterThan(BigDecimal.valueOf(low), BigDecimal.valueOf(high))
                .stream()
                .map(b -> String.format("%s - $%.2f", b.getTitle(), b.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksNotReleasedInGivenYear(int year) {
        return bookRepository.findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate.of(year, 01, 01), LocalDate.of(year, 12, 31))
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksReleasedBeforeDate(LocalDate date) {
        return this.bookRepository.findAllByReleaseDateBefore(date)
                .stream()
                .map(b -> String.format("%s %s %.2f",
                        b.getTitle(),
                        b.getEditionType().name(),
                        b.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksWhoseTitleContains(String part) {
        return bookRepository.findAllByTitleContaining(part)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBookWhoseAuthorLastNameStartsWith(String part) {
        return bookRepository.findAllByAuthorLastNameStartsWith(part)
                .stream()
                .map(Book::getTitle)
                .toList();
    }

    @Override
    public int findTitlesCountLongerThan(int minTitleLength) {
        return bookRepository.countByTitleLengthGreaterThan(minTitleLength);
    }

    @Override
    public BookInfo findInfoByTitle(String givenTitle) {
        return bookRepository.findByTitle(givenTitle);
    }

    @Override
    public int upadateBookCopiesReleasedAfter(int copiesToAdd, LocalDate localDate) {
        return bookRepository.updateBookCopiesReleasedAfter(copiesToAdd, localDate);
    }


}
