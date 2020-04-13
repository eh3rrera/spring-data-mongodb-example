package com.example.demo.dto;

import com.example.demo.domain.DifficultyLevel;
import com.example.demo.domain.Recipe;

/**
 * DTO class for service parameters
 */
public class ParamsDto {
    private Long recipeId;

    private DifficultyLevel difficultyLevel;

    private Integer underMinutes;

    private Integer minMinutes;

    private Integer maxMinutes;

    private Integer lastRecipes;

    private String textSearch;

    private Recipe recipe;

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public Integer getUnderMinutes() {
        return underMinutes;
    }

    public void setUnderMinutes(Integer underMinutes) {
        this.underMinutes = underMinutes;
    }

    public Integer getMinMinutes() {
        return minMinutes;
    }

    public void setMinMinutes(Integer minMinutes) {
        this.minMinutes = minMinutes;
    }

    public Integer getMaxMinutes() {
        return maxMinutes;
    }

    public void setMaxMinutes(Integer maxMinutes) {
        this.maxMinutes = maxMinutes;
    }

    public Integer getLastRecipes() {
        return lastRecipes;
    }

    public void setLastRecipes(int lastRecipes) {
        this.lastRecipes = lastRecipes;
    }

    public String getTextSearch() {
        return textSearch;
    }

    public void setTextSearch(String textSearch) {
        this.textSearch = textSearch;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public String toString() {
        return "ParamsDto{" +
                "recipeId=" + recipeId +
                ", difficultyLevel=" + difficultyLevel +
                ", underMinutes=" + underMinutes +
                ", minMinutes=" + minMinutes +
                ", maxMinutes=" + maxMinutes +
                ", lastRecipes=" + lastRecipes +
                ", textSearch='" + textSearch + '\'' +
                ", recipe=" + recipe +
                '}';
    }
}
