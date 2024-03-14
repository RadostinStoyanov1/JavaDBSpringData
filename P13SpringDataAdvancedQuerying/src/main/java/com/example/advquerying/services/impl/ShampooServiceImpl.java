package com.example.advquerying.services.impl;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import com.example.advquerying.repositories.ShampooRepository;
import com.example.advquerying.services.ShampooService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShampooServiceImpl implements ShampooService {

    private final ShampooRepository shampooRepository;

    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public List<String> findAllShampoosByGivenSize(String size) {
        Size sizeEnum = Size.valueOf(size.toUpperCase());
        return this.shampooRepository.findAllBySizeOrderById(sizeEnum)
                .stream()
                .map(s -> String.format("%s %s %.2flv.",
                        s.getBrand(),
                        s.getSize().name(),
                        s.getPrice().doubleValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllShampoosContainingIngredient(List<String> strings) {
        Set<Shampoo> allByIngredientsNameIn = this.shampooRepository.findAllByIngredientsNameIn(strings);
        return allByIngredientsNameIn.stream().map(Shampoo::getBrand).collect(Collectors.toList());
    }

    @Override
    public List<String> getAllShampoosBySizeOrLabelIdOrderedByPrice(String size, long id) {
        Size enumSize = Size.valueOf(size.toUpperCase());
        Set<Shampoo> allBySizeOrLabelIdOrderedByPrice = shampooRepository.findAllBySizeOrLabelIdOrderByPrice(enumSize, id);
        return allBySizeOrLabelIdOrderedByPrice.stream()
                .map(s -> String.format("%s %s %.2flv.",
                        s.getBrand(),
                        s.getSize().name(),
                        s.getPrice().doubleValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllShampoosWithPriceHigherThan(Double price) {
        Set<Shampoo> shampoosWithPriceHigherThan = this.shampooRepository.findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal.valueOf(price));
        return shampoosWithPriceHigherThan.stream()
                .map(s -> String.format("%s %s %.2flv.",
                        s.getBrand(),
                        s.getSize().name(),
                        s.getPrice().doubleValue()))
                .collect(Collectors.toList());
    }

    @Override
    public int getCountOfShampoosWithPriceLowerThan(double priceLimit) {
        return shampooRepository.findAllByPriceLessThan(BigDecimal.valueOf(priceLimit)).size();
    }

    @Override
    public List<String> getShampoosWithIngredientsCountLessThan(Integer count) {
        return this.shampooRepository.ShampoosWithIngredientsCountLEssThan(count)
                .stream()
                .map(s -> s.getBrand())
                .collect(Collectors.toList());
    }
}
