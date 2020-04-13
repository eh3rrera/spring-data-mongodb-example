package com.example.demo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Document("recipes")
public class Recipe {
    @Id
    private String id;

    @Field("recipe_id")
    @Indexed(unique = true)
    private long recipeId;

    @TextIndexed(weight = 2)
    private String title;

    @TextIndexed
    private String instructions;

    @Field("difficulty_level")
    private DifficultyLevel difficultyLevel;

    @Field("preparation_time")
    private Integer preparationTimeMin;

    @Field("last_cooked")
    private Date lastCooked;

    @Transient
    private String lastCookedString;

    @DBRef
    private List<Ingredient> ingredients;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(long recipeId) {
        this.recipeId = recipeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public Integer getPreparationTimeMin() {
        return preparationTimeMin;
    }

    public void setPreparationTimeMin(Integer preparationTimeMin) {
        this.preparationTimeMin = preparationTimeMin;
    }

    public Date getLastCooked() {
        return lastCooked;
    }

    public void setLastCooked(Date lastCooked) {
        this.lastCooked = lastCooked;
    }

    public String getLastCookedString() {
        String date = "";
        if(lastCooked != null) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            date = df.format(lastCooked);
        }

        return date;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id='" + id + '\'' +
                ", recipeId=" + recipeId +
                ", title='" + title + '\'' +
                ", instructions='" + instructions + '\'' +
                ", difficultyLevel=" + difficultyLevel +
                ", preparationTimeMin=" + preparationTimeMin +
                ", lastCookedString='" + lastCookedString + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }
}
