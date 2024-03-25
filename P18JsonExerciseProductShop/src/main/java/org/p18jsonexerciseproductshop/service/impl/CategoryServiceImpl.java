package org.p18jsonexerciseproductshop.service.impl;

import com.google.gson.Gson;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.p18jsonexerciseproductshop.data.entities.Category;
import org.p18jsonexerciseproductshop.data.entities.Product;
import org.p18jsonexerciseproductshop.data.repositories.CategoryRepository;
import org.p18jsonexerciseproductshop.service.CategoryService;
import org.p18jsonexerciseproductshop.service.dtos.CategoryByProductCountDto;
import org.p18jsonexerciseproductshop.service.dtos.CategorySeedDto;
import org.p18jsonexerciseproductshop.util.ValidationUtil;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    //Constants
    private static final String FILE_PATH = "C:\\Rado\\Intellij Tasks Spring Data\\P18JsonExerciseProductShop\\src\\main\\resources\\09. DB-Advanced-JSON-Processing-Exercises-Resources\\09. DB-Advanced-JSON-Processing-Exercises\\categories.json";

    //Beans
    private final CategoryRepository categoryRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedCategories() throws IOException {
        if (this.categoryRepository.count() == 0) {
            String jsonContent = new String(Files.readAllBytes(Path.of(FILE_PATH)));
            //this.gson.fromJson(new FileReader(FILE_PATH), CategorySeedDto[].class);

            CategorySeedDto[] categoriesSeedDto = this.gson.fromJson(jsonContent, CategorySeedDto[].class);
            for (CategorySeedDto categorySeedDto : categoriesSeedDto) {
                if (!this.validationUtil.isValid(categorySeedDto)) {
                    this.validationUtil.getViolations(categorySeedDto)
                            .forEach(v -> System.out.println(v.getMessage()));
                    continue;
                }
                Category category = this.modelMapper.map(categorySeedDto, Category.class);
                this.categoryRepository.saveAndFlush(category);
            }
        }
    }

    @Override
    public List<CategoryByProductCountDto> getAllCategoriesByProductCount() {
        return this.categoryRepository.findAllCategoriesByProductCount()
                .stream()
                .map(c -> {
                    CategoryByProductCountDto categoryDto = new CategoryByProductCountDto();
                    categoryDto.setCategory(c.getName());
                    categoryDto.setProductsCount(c.getProducts().size());
                    BigDecimal sum = BigDecimal.valueOf(c.getProducts().stream().mapToDouble(p -> p.getPrice().doubleValue()).sum());
                    categoryDto.setAveragePrice(sum.divide(BigDecimal.valueOf(c.getProducts().size()), MathContext.DECIMAL64));
                    categoryDto.setTotalRevenue(sum);
                    return categoryDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void printAllCategoriesByProductCount() {
        System.out.println(this.gson.toJson(this.getAllCategoriesByProductCount()));
    }
}
