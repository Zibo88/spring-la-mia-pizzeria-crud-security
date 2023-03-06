package com.corsojava.pizzeria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.corsojava.pizzeria.model.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

}
