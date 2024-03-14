package com.example.advquerying.services;

import com.example.advquerying.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface IngredientService {

    List<String> getAllIngredientsWithNameStartingWith(String prefix);
    List<String> getAllIngredientsWithNamesIn(List<String> ingredientNames);

    int deleteIngredientByName(String name);

    int updatedIngredientsPrice(Double multiplicator);

    int updatedIngredientsPriceWithNamesIn(Double percent, List<String> ingredientsNames);
}
