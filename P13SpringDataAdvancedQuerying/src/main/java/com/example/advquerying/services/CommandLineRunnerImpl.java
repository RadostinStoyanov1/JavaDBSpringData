package com.example.advquerying.services;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final ShampooService shampooService;
    private final IngredientService ingredientService;


    public CommandLineRunnerImpl(ShampooService shampooService, IngredientService ingredientService) {
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }

    @Override
    public void run(String... args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        //this.shampooService.findAllShampoosByGivenSize(reader.readLine())
        //        .forEach(System.out::println);
        //this.shampooService.getAllShampoosContainingIngredient(List.of(reader.readLine().split("\\s+")))
        //        .forEach(System.out::println);
        //this.shampooService.getAllShampoosBySizeOrLabelIdOrderedByPrice("medium", 10)
        //        .forEach(System.out::println);
        //this.shampooService.getAllShampoosWithPriceHigherThan(Double.parseDouble(reader.readLine()))
        //        .forEach(System.out::println);
        //this.ingredientService.getAllIngredientsWithNameStartingWith("M")
        //        .forEach(System.out::println);
        //List<String> inputIngredientsList = new ArrayList<>();
        //String line = reader.readLine();
        //while (line.length() > 0) {
        //    inputIngredientsList.add(line);
        //    line = reader.readLine();
        //}
        //this.ingredientService.getAllIngredientsWithNamesIn(inputIngredientsList)
        //        .forEach(System.out::println);
        //System.out.println(this.shampooService.getCountOfShampoosWithPriceLowerThan(8.50));
        //this.shampooService.getShampoosWithIngredientsCountLessThan(2)
        //        .forEach(System.out::println);
        //System.out.println(this.ingredientService.deleteIngredientByName("Nettle"));
        //System.out.println(this.ingredientService.updatedIngredientsPrice(1.10));
        System.out.println(this.ingredientService
                .updatedIngredientsPriceWithNamesIn(1.10, List.of("Apple", "Herbs", "Lavender")));
    }
}
