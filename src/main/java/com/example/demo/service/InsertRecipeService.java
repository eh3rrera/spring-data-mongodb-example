package com.example.demo.service;

import com.example.demo.domain.Recipe;
import com.example.demo.dto.ParamsDto;
import com.example.demo.util.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class InsertRecipeService implements RecipeService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public String execute(ParamsDto paramsDto) {
        Recipe recipe = paramsDto.getRecipe();
        recipe.setRecipeId(Counter.getValue());

        recipe = mongoTemplate.insert(recipe);

        return "Recipe inserted with ID " + recipe.getRecipeId();
    }
}
