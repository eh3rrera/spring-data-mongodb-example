package com.example.demo.repository;

import com.example.demo.domain.DifficultyLevel;
import com.example.demo.domain.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RecipeRepository extends MongoRepository<Recipe, String> {

    List<Recipe> findByDifficultyLevel(DifficultyLevel level);

    @Query("{'preparationTimeMin' : {$lte: ?0}}")
    List<Recipe> findRecipesUnderSpecificTime(int timeInMinutes);
}
