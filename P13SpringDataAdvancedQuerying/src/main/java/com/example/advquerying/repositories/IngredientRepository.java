package com.example.advquerying.repositories;

import com.example.advquerying.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Set<Ingredient> getIngredientsByNameStartsWith(String prefix);
    //Set<Ingredient> getIngredientsByNameIn(List<String> ingredientNames);
    Set<Ingredient> findAllByNameIn(List<String> ingredientNames);

    @Modifying
    @Transactional
    @Query("DELETE Ingredient WHERE name = :name")
    int deleteIngredientByName(String name);

    @Modifying
    @Transactional
    @Query("UPDATE Ingredient SET price = price * :multiplicator")
    int increasePriceOfAllIngredientsByMultiplicator(BigDecimal multiplicator);

    @Modifying
    @Transactional
    @Query("UPDATE Ingredient SET price = price * :percent WHERE name IN :ingredients")
    int updateIngredientsBy10PercentsWhereNamesIn(BigDecimal percent, List<String> ingredients);
}
