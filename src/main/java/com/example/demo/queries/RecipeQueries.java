package com.example.demo.queries;

import com.example.demo.domain.Recipe;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RecipeQueries {
    private MongoTemplate mongoTemplate;

    public RecipeQueries(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Recipe> findByPreparationTimeBetween(int minMinutes,
                                                     int maxMinutes) {
        Query byPreparationTimeBetween = Query
                .query(Criteria.where("preparationTimeMin")
                        .gte(minMinutes)
                        .lte(maxMinutes))
                .with(Sort.by(Sort.Direction.DESC, "preparationTimeMin"));

        return this.mongoTemplate.find(byPreparationTimeBetween, Recipe.class);
    }

    public List<Recipe> search(String text) {
        // https://docs.mongodb.com/manual/reference/text-search-languages/
        TextCriteria textCriteria = TextCriteria
                .forLanguage("en")
                .matching(text);

        Query byText = TextQuery.queryText(textCriteria)
                .sortByScore()
                .with(PageRequest.of(0, 10));

        return this.mongoTemplate.find(byText, Recipe.class);
    }

    public long updateLastCooked(long recipeId) {
        Query recipeQuery = Query.query(
                Criteria.where("recipeId").is(recipeId)
        );

        Update setNow = Update.update("lastCooked", new Date());

        UpdateResult result = this.mongoTemplate.updateFirst(
                recipeQuery,
                setNow,
                Recipe.class);

        return result.getModifiedCount();
    }
}
