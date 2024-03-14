package com.example.advquerying.repositories;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {
    Set<Shampoo> findAllBySizeOrderById(Size size);

    @Query("SELECT s FROM Shampoo s JOIN s.ingredients i WHERE i.name IN(:names)")
    Set<Shampoo> findAllByIngredientsNameIn(@Param("names") List<String> names);

    Set<Shampoo> findAllBySizeOrLabelIdOrderByPrice(Size size, Long labelId);

    Set<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    Set<Shampoo> findAllByPriceLessThan(BigDecimal price);

    @Query("FROM Shampoo s WHERE SIZE(s.ingredients) < :number")
    Set<Shampoo> ShampoosWithIngredientsCountLEssThan(Integer number);
}
