package com.example.demo.service;

import com.example.demo.dto.ParamsDto;
import com.example.demo.queries.RecipeQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarkRecipeService implements RecipeService {
    @Autowired
    private RecipeQueries recipeQueries;

    @Override
    public String execute(ParamsDto paramsDto) {
        String result;
        long modifiedCount = recipeQueries.updateLastCooked(paramsDto.getRecipeId());

        if(modifiedCount > 0) {
            result = "Recipe with ID " + paramsDto.getRecipeId() + " was marked as cooked today";
        } else {
            result = "No recipe found with ID " + paramsDto.getRecipeId();
        }

        return result;
    }
}
