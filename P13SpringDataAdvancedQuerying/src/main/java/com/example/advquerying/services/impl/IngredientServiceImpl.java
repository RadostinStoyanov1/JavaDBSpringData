package com.example.advquerying.services.impl;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.repositories.IngredientRepository;
import com.example.advquerying.services.IngredientService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<String> getAllIngredientsWithNameStartingWith(String prefix) {
        return this.ingredientRepository.getIngredientsByNameStartsWith(prefix)
                .stream()
                .map(Ingredient::getName)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllIngredientsWithNamesIn(List<String> ingredientNames) {
        return this.ingredientRepository.findAllByNameIn(ingredientNames)
                .stream()
                .map(Ingredient::getName)
                .collect(Collectors.toList());
    }

    @Override
    public int deleteIngredientByName(String name) {
        return this.ingredientRepository.deleteIngredientByName(name);
    }

    @Override
    public int updatedIngredientsPrice(Double multiplicator) {
        return this.ingredientRepository
                .increasePriceOfAllIngredientsByMultiplicator(BigDecimal.valueOf(multiplicator));
    }

    @Override
    public int updatedIngredientsPriceWithNamesIn(Double percent, List<String> ingredientsNames) {
        return this.ingredientRepository.updateIngredientsBy10PercentsWhereNamesIn(BigDecimal.valueOf(percent), ingredientsNames);
    }

}
