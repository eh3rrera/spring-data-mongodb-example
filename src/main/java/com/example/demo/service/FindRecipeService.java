package com.example.demo.service;

import com.example.demo.domain.DifficultyLevel;
import com.example.demo.domain.Recipe;
import com.example.demo.dto.ParamsDto;
import com.example.demo.queries.RecipeQueries;
import com.example.demo.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.demo.util.RecipeUtil.*;

@Service
public class FindRecipeService implements RecipeService {
    @Autowired
    private RecipeQueries recipeQueries;

    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public String execute(ParamsDto paramsDto) {
        String result = "No recipe found";

        // find by id
        if(paramsDto.getRecipeId() != null) {
            result = findById(paramsDto.getRecipeId());
        }
        // find by difficulty
        else if(paramsDto.getDifficultyLevel() != null) {
            result = findByDifficulty(paramsDto.getDifficultyLevel());
        }
        // find under prep time
        else if(paramsDto.getUnderMinutes() != null) {
            result = findUnderMinutes(paramsDto.getUnderMinutes());
        }
        // find last n recipes
        else if(paramsDto.getLastRecipes() != null) {
            result = findLastRecipes(paramsDto.getLastRecipes());
        }
        // find between x and y min of prep time
        else if(paramsDto.getMaxMinutes() != null && paramsDto.getMinMinutes() != null) {
            result = findBetweenMinutes(paramsDto.getMinMinutes(), paramsDto.getMaxMinutes());
        }

        return result;
    }

    private String findById(long recipeId) {
        Optional<Recipe> recipeOpt = Optional.empty();
        String result;

        Recipe recipe = new Recipe();
        recipe.setRecipeId(recipeId);
        Example<Recipe> example = Example.of(recipe);
        recipeOpt = recipeRepository.findOne(example);

        if(recipeOpt.isPresent()) {
            result = formatRecipe(recipeOpt.get());
        } else {
            result = "No recipe found with ID " + recipeId;
        }

        return result;
    }

    private String findByDifficulty(DifficultyLevel level) {
        String result;

        List<Recipe> list = recipeRepository.findByDifficultyLevel(level);

        if(list != null && list.size() > 0) {
            result = formatRecipeList(list);
        } else {
            result = "No recipes found with difficulty level " + level;
        }

        return result;
    }

    private String findUnderMinutes(int minutes) {
        String result;

        List<Recipe> list = recipeRepository.findRecipesUnderSpecificTime(minutes);

        if(list != null && list.size() > 0) {
            result = formatRecipeList(list);
        } else {
            result = "No recipes found prepared under " + minutes + " minutes";
        }

        return result;
    }

    private String findLastRecipes(int nLastRecipes) {
        String result;
        List<Recipe> list = null;

        Pageable sortedByRecipeIdDesc = PageRequest.of(0, nLastRecipes, Sort.by("recipeId").descending());
        Page<Recipe> pageList = recipeRepository.findAll(sortedByRecipeIdDesc);
        list = pageList.toList();

        if(list != null && list.size() > 0) {
            result = formatRecipeList(list);
        } else {
            result = "No recipes found";
        }

        return result;
    }

    private String findBetweenMinutes(int minMinutes, int maxMinutes) {
        String result;

        List<Recipe> list = recipeQueries.findByPreparationTimeBetween(minMinutes, maxMinutes);

        if(list != null && list.size() > 0) {
            result = formatRecipeList(list);
        } else {
            result = "No recipes found prepared between " + minMinutes + " and " + maxMinutes + " minutes";
        }

        return result;
    }

}