package p12springdataintrobookshop.service.Impl;

import org.springframework.stereotype.Service;
import p12springdataintrobookshop.data.entities.Author;
import p12springdataintrobookshop.data.repositories.AuthorRepository;
import p12springdataintrobookshop.service.AuthorService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private static final String FILE_PATH = "C:\\Rado\\Intellij Tasks Spring Data\\P12SpringDataIntroBookshop\\src\\main\\resources\\files\\authors.txt";
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthors() throws IOException {
        if(this.authorRepository.count() == 0) {
            Files.readAllLines(Path.of(FILE_PATH))
                    .stream()
                    .filter(row -> !row.isEmpty())
                    .forEach(row -> {
                        String[] tokens = row.split("\\s+");
                        this.authorRepository.saveAndFlush(new Author(tokens[0], tokens[1]));
                    });
        }
    }

    @Override
    public Author getRandomAuthor() {
        int randomId = ThreadLocalRandom.current().nextInt(1, (int) this.authorRepository.count() + 1);
        return this.authorRepository.findById(randomId).get();
    }

    @Override
    public List<String> getAllAuthorsFirstAndLastNameForBooksBeforeYear1990() {
        return authorRepository.findAllByBooksReleaseDateBefore(LocalDate.of(1990, 1, 1))
                .stream()
                .map(author -> String.format("%s %s", author.getFirstName(), author.getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllByOrderByBooksSizeDesc() {
        Set<Author> authors = this.authorRepository.findAllByOrderByBooksSizeDesc();

        return authors
                .stream()
                .map(a -> String.format("%s %s %d books", a.getFirstName(), a.getLastName(), a.getBooks().size()))
                .collect(Collectors.toList());
    }
}
