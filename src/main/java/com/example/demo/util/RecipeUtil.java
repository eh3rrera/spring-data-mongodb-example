package com.example.demo.util;

import com.example.demo.domain.Ingredient;
import com.example.demo.domain.Recipe;

import java.util.List;

import static com.example.demo.util.ValidationUtils.*;

public class RecipeUtil {

    public static String formatRecipe(Recipe recipe) {
        String headerFormat = "%-6s %-25s %-20s %-20s %-15s";
        String result = String.format(headerFormat, "ID", "TITLE", "DIFFICULTY LEVEL", "PREPARATION TIME", "LAST COOKED") + "\n";
        result += String.format(headerFormat,
                recipe.getRecipeId(),
                recipe.getTitle(),
                recipe.getDifficultyLevel(),
                recipe.getPreparationTimeMin(),
                recipe.getLastCooked() != null ? recipe.getLastCookedString() : ""
        );
        result += "\nInstructions:\n" + recipe.getInstructions() + "\nIngredients:\n";
        for (Ingredient ingredient : recipe.getIngredients()) {
            result += ingredient.getQuantity() > 0 ? ingredient.getQuantity() + " " : "";
            result += isEmpty(ingredient.getUnit()) ? "" : ingredient.getUnit() + " ";
            result += ingredient.getDescription() + "\n";
        }

        return result;
    }

    public static String formatRecipeList(List<Recipe> list) {
        String result = "Results found: " + list.size() + "\n";
        String headerFormat = "%-6s %-6s";
        result += String.format(headerFormat, "ID", "TITLE") + "\n";
        for (Recipe recipe : list) {
            result += String.format(headerFormat,
                    recipe.getRecipeId(),
                    recipe.getTitle());
            result += "\n" + recipe.getInstructions() + "\n-------------\n";
        }

        return result;
    }
}
