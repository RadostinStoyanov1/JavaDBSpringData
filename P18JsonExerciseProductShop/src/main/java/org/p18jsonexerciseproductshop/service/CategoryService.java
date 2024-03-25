package org.p18jsonexerciseproductshop.service;

import org.p18jsonexerciseproductshop.service.dtos.CategoryByProductCountDto;

import java.io.IOException;
import java.util.List;

public interface CategoryService {
    void seedCategories() throws IOException;

    List<CategoryByProductCountDto> getAllCategoriesByProductCount();

    void printAllCategoriesByProductCount();
}
