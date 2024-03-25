package org.p18jsonexerciseproductshop.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.p18jsonexerciseproductshop.data.entities.Category;
import org.p18jsonexerciseproductshop.data.entities.Product;
import org.p18jsonexerciseproductshop.data.entities.User;
import org.p18jsonexerciseproductshop.data.repositories.CategoryRepository;
import org.p18jsonexerciseproductshop.data.repositories.ProductRepository;
import org.p18jsonexerciseproductshop.data.repositories.UserRepository;
import org.p18jsonexerciseproductshop.service.ProductService;
import org.p18jsonexerciseproductshop.service.dtos.ProductInRangeDto;
import org.p18jsonexerciseproductshop.service.dtos.ProductSeedDto;
import org.p18jsonexerciseproductshop.util.ValidationUtil;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private static final String FILE_PATH = "C:\\Rado\\Intellij Tasks Spring Data\\P18JsonExerciseProductShop\\src\\main\\resources\\09. DB-Advanced-JSON-Processing-Exercises-Resources\\09. DB-Advanced-JSON-Processing-Exercises\\products.json";

    private final ProductRepository productRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedProducts() throws FileNotFoundException {
        if (this.productRepository.count() == 0) {
            ProductSeedDto[] productSeedDto = this.gson.fromJson(new FileReader(FILE_PATH), ProductSeedDto[].class);

            for (ProductSeedDto seedProduct : productSeedDto) {
                if (!this.validationUtil.isValid(seedProduct)) {
                    this.validationUtil.getViolations(seedProduct)
                            .forEach(v -> System.out.println(v.getMessage()));
                    continue;
                }

                Product product = this.modelMapper.map(seedProduct, Product.class);
                product.setBuyer(getRandomUser(false));
                product.setSeller(getRandomUser(true));
                product.setCategories(getRandomCategories());

                this.productRepository.saveAndFlush(product);
            }
        }
    }

    @Override
    public List<ProductInRangeDto> getAllProductsInRange(BigDecimal from, BigDecimal to) {
        return this.productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPrice(from, to)
                .stream()
                .map(p -> {
                    ProductInRangeDto dto = this.modelMapper.map(p, ProductInRangeDto.class);
                    dto.setSeller(String.format("%s %s", p.getSeller().getFirstName(), p.getSeller().getLastname()));
                    return dto;
                })
                .sorted(Comparator.comparing(ProductInRangeDto::getPrice))
                .collect(Collectors.toList());
    }

    @Override
    public void printAllProductsInRange(BigDecimal from, BigDecimal to) {
        System.out.println(this.gson.toJson(this.getAllProductsInRange(from, to)));
    }

    private Set<Category> getRandomCategories() {
        Set<Category> categories = new HashSet<>();
        int randomCount = ThreadLocalRandom.current().nextInt(1,4);
        for (int i = 1; i <= randomCount; i++) {
            long categoryId = ThreadLocalRandom.current().nextInt(1, (int) this.categoryRepository.count() + 1);
            categories.add(this.categoryRepository.findById(categoryId).get());
        }
        return categories;
    }

    private User getRandomUser(boolean isSeller) {
        long randomId = ThreadLocalRandom.current().nextLong(1, this.userRepository.count() + 1);

        return !isSeller && randomId % 4 == 0 ? null : this.userRepository.findById(randomId).get();
    }
}
