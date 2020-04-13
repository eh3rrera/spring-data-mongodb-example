package com.example.demo.service;

import com.example.demo.domain.Recipe;
import com.example.demo.dto.ParamsDto;
import com.example.demo.queries.RecipeQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.util.RecipeUtil.*;

@Service
public class SearchRecipeService implements RecipeService {
    @Autowired
    private RecipeQueries recipeQueries;

    @Override
    public String execute(ParamsDto paramsDto) {
        String result;
        String text = paramsDto.getTextSearch();
        List<Recipe> list = recipeQueries.search(text);

        if(list != null && list.size() > 0) {
            result = formatRecipeList(list);
        } else {
            result = "No results found for search term '" + paramsDto.getTextSearch() + "'";
        }

        return result;
    }
}
