package org.p18jsonexerciseproductshop.service;

import org.p18jsonexerciseproductshop.service.dtos.ProductInRangeDto;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    void seedProducts() throws FileNotFoundException;

    List<ProductInRangeDto> getAllProductsInRange(BigDecimal from, BigDecimal to);

    void printAllProductsInRange(BigDecimal from, BigDecimal to);
}
