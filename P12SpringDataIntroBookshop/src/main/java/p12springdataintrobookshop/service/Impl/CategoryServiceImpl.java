package p12springdataintrobookshop.service.Impl;

import org.springframework.stereotype.Service;
import p12springdataintrobookshop.data.entities.Category;
import p12springdataintrobookshop.data.repositories.CategoryRepository;
import p12springdataintrobookshop.service.CategoryService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final String FILE_PATH = "C:\\Rado\\Intellij Tasks Spring Data\\P12SpringDataIntroBookshop\\src\\main\\resources\\files\\categories.txt";
    final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategories() throws IOException {
        if (this.categoryRepository.count() == 0) {
            Files.readAllLines(Path.of(FILE_PATH))
                    .stream()
                    .filter(row -> !row.isEmpty())
                    .forEach(row -> categoryRepository.saveAndFlush(new Category(row)));
        }
    }

    @Override
    public Set<Category> getRandomCategories() {
        Set<Category> categories = new HashSet<>();

        int randomCount = ThreadLocalRandom
                .current()
                .nextInt(1, 4);
        for (int i = 1; i <= randomCount; i++) {
            int randomId = ThreadLocalRandom
                    .current()
                    .nextInt(1, (int) this.categoryRepository.count() + 1);
            categories.add(this.categoryRepository.findById(randomId).get());
        }
        return categories;
    }
}
