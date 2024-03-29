package p12springdataintrobookshop.service;

import p12springdataintrobookshop.data.entities.Category;

import java.io.IOException;
import java.util.Set;

public interface CategoryService {

    void seedCategories() throws IOException;

    Set<Category> getRandomCategories();
}
