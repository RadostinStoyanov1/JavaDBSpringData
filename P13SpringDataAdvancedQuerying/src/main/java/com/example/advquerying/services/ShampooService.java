package com.example.advquerying.services;

import com.example.advquerying.entities.Shampoo;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ShampooService {
    List<String> findAllShampoosByGivenSize(String size);

    List<String> getAllShampoosContainingIngredient(List<String> strings);

    List<String> getAllShampoosBySizeOrLabelIdOrderedByPrice(String size, long id);

    List<String> getAllShampoosWithPriceHigherThan(Double price);
    int getCountOfShampoosWithPriceLowerThan(double priceLimit);

    List<String> getShampoosWithIngredientsCountLessThan(Integer count);
}

