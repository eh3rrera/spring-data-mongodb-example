package com.example.demo.config;

import com.example.demo.domain.DifficultyLevel;
import com.example.demo.domain.Ingredient;
import com.example.demo.domain.Recipe;
import com.example.demo.util.Counter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.index.MongoPersistentEntityIndexResolver;
import org.springframework.data.mongodb.core.mapping.BasicMongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Order(1)
public class DatabaseSeederRunner implements CommandLineRunner {
    private MongoTemplate mongoTemplate;

    private MongoConverter mongoConverter;

    public DatabaseSeederRunner(MongoTemplate mongoTemplate, MongoConverter mongoConverter) {
        this.mongoTemplate = mongoTemplate;
        this.mongoConverter = mongoConverter;
    }

    @Override
    public void run(String... args) {
        createIndexes();
        loadData();
    }

    private void createIndexes() {
        MappingContext mappingContext = this.mongoConverter.getMappingContext();

        if (mappingContext instanceof MongoMappingContext) {
            MongoMappingContext mongoMappingContext = (MongoMappingContext) mappingContext;
            for (BasicMongoPersistentEntity<?> persistentEntity : mongoMappingContext.getPersistentEntities()) {
                Class clazz = persistentEntity.getType();
                if (clazz.isAnnotationPresent(Document.class)) {
                    var resolver = new MongoPersistentEntityIndexResolver(mongoMappingContext);

                    var indexOps = this.mongoTemplate.indexOps(clazz);
                    resolver.resolveIndexFor(clazz).forEach(indexOps::ensureIndex);
                }
            }
        }
    }

    private void loadData() {
        // Recipe 1 - https://www.bbcgoodfood.com/recipes/summer-winter-chicken
        List<Ingredient> ingredients1 = new ArrayList<>();
        ingredients1.add(new Ingredient(1.0, "tbsp", "olive oil"));
        ingredients1.add(new Ingredient(4.0, "", "boneless skinless chicken breasts"));
        ingredients1.add(new Ingredient(200.0, "grams", "pack cherry tomatoes"));
        ingredients1.add(new Ingredient(3.0, "tbsp", "pesto"));
        ingredients1.add(new Ingredient(3.0, "tbsp", "crème fraîche "));
        ingredients1.add(new Ingredient(1.0, "", "fresh basil Basil if you have it"));

        Recipe recipe1 = new Recipe();
        recipe1.setDifficultyLevel(DifficultyLevel.EASY);
        recipe1.setInstructions("Heat the oil in a frying pan, preferably non-stick. Add the chicken and fry without moving it until it takes on a bit of colour. Turn the chicken and cook on the other side. Continue cooking for 12-15 mins until the chicken is cooked through. Season all over with a little salt and pepper. Halve the tomatoes and throw them into the pan, stirring them around for a couple of minutes until they start to soften. Reduce the heat and stir in the pesto and crème fraîche until it makes a sauce. Scatter with a few basil leaves if you have them, then serve with rice and salad or mash and broccoli.");
        recipe1.setPreparationTimeMin(10);
        recipe1.setRecipeId(Counter.getValue());
        recipe1.setIngredients(ingredients1);
        recipe1.setTitle("Seasoned Chicken");

        // Recipe 2 - https://www.bbcgoodfood.com/recipes/super-quick-fish-curry
        List<Ingredient> ingredients2 = new ArrayList<>();
        ingredients2.add(new Ingredient(1.0, "tbsp", "vegetable oil"));
        ingredients2.add(new Ingredient(1.0, "", "large onion, chopped"));
        ingredients2.add(new Ingredient(1.0, "", "garlic clove, chopped"));
        ingredients2.add(new Ingredient(1.0, "tbsp", "curry paste"));
        ingredients2.add(new Ingredient(400.0, "grams", "can tomato"));
        ingredients2.add(new Ingredient(200.0, "ml", "vegetable stock"));
        ingredients2.add(new Ingredient(0.0, "", "white fish fillets, skinned and cut into big chunks"));
        ingredients2.add(new Ingredient(0.0, "", "rice or naan bread"));

        Recipe recipe2 = new Recipe();
        recipe2.setDifficultyLevel(DifficultyLevel.NORMAL);
        recipe2.setInstructions("Heat the oil in a deep pan and gently fry the onion and garlic for about 5 mins until soft. Add the curry paste and stir-fry for 1-2 mins, then tip in the tomatoes and stock. Bring to a simmer, then add the fish. Gently cook for 4-5 mins until the fish flakes easily. Serve immediately with rice.");
        recipe2.setPreparationTimeMin(7);
        recipe2.setRecipeId(Counter.getValue());
        recipe2.setIngredients(ingredients2);
        recipe2.setTitle("Fish Curry");

        // Recipe 3 - https://www.bbcgoodfood.com/recipes/asparagus-meatball-orzo
        List<Ingredient> ingredients3 = new ArrayList<>();
        ingredients3.add(new Ingredient(12.0, "", "pork meatballs"));
        ingredients3.add(new Ingredient(500.0, "grams", "orzo pasta"));
        ingredients3.add(new Ingredient(0.0, "", "asparagus sliced in half lengthways"));
        ingredients3.add(new Ingredient(200.0, "grams", "tub crème fraîche"));

        Recipe recipe3 = new Recipe();
        recipe3.setDifficultyLevel(DifficultyLevel.HARD);
        recipe3.setInstructions("Heat oven to 180C/160C fan/gas 4. Put the meatballs on a tray lined with foil, season and cook for 20 mins until cooked through. Meanwhile, bring a pan of salted water to the boil, add the orzo and cook for 4 mins, then add the asparagus and simmer for 4 mins more. Drain, then tip back into the pan along with the meatballs and crème fraîche, mix and season well.");
        recipe3.setPreparationTimeMin(25);
        recipe3.setRecipeId(Counter.getValue());
        recipe3.setIngredients(ingredients3);
        recipe3.setTitle("Asparagus and meatball orzo");

        // Recipe 4 - https://www.bbcgoodfood.com/recipes/easy-teriyaki-chicken
        List<Ingredient> ingredients4 = new ArrayList<>();
        ingredients4.add(new Ingredient(2.0, "tbsp", "toasted sesame oil"));
        ingredients4.add(new Ingredient(6.0, "", "skinless and boneless chicken thighs, sliced"));
        ingredients4.add(new Ingredient(2.0, "", "large garlic cloves, crushed"));
        ingredients4.add(new Ingredient(1.0, "", "thumb-sized piece ginger, grated"));
        ingredients4.add(new Ingredient(50.0, "grams", "runny honey"));
        ingredients4.add(new Ingredient(30.0, "ml", "light soy sauce"));
        ingredients4.add(new Ingredient(1.0, "tbsp", "rice wine vinegar"));
        ingredients4.add(new Ingredient(1.0, "tbsp", "sesame seeds, to serve"));
        ingredients4.add(new Ingredient(4.0, "", "spring onions, shredded, to serve"));
        ingredients4.add(new Ingredient(0.0, "", "sticky rice, to serve"));
        ingredients4.add(new Ingredient(0.0, "", "steamed bok choi or spring greens, to serve"));

        Recipe recipe4 = new Recipe();
        recipe4.setDifficultyLevel(DifficultyLevel.NORMAL);
        recipe4.setInstructions("Heat the oil in a non-stick pan over a medium heat. Add the chicken and fry for 7 mins, or until golden. Add the garlic and ginger and fry for 2 mins. Stir in the honey, soy sauce, vinegar and 100ml water. Bring to the boil and cook for 2 - 5 mins over a medium heat until the chicken is sticky and coated in a thick sauce. Scatter over the spring onions and sesame seeds, then serve the chicken with the rice and steamed veg.");
        recipe4.setPreparationTimeMin(15);
        recipe4.setRecipeId(Counter.getValue());
        recipe4.setIngredients(ingredients4);
        recipe4.setTitle("Teriyaki chicken");

        // Recipe 5 - https://www.bbcgoodfood.com/recipes/butter-bean-chorizo-stew
        List<Ingredient> ingredients5 = new ArrayList<>();
        ingredients5.add(new Ingredient(200.0, "grams", "chorizo"));
        ingredients5.add(new Ingredient(800.0, "grams", "chopped tomatoes"));
        ingredients5.add(new Ingredient(800.0, "grams", "drained butter beans"));
        ingredients5.add(new Ingredient(1.0, "tub", "fresh pesto"));

        Recipe recipe5 = new Recipe();
        recipe5.setDifficultyLevel(DifficultyLevel.EASY);
        recipe5.setInstructions("Slice the chorizo and tip into a large saucepan over a medium heat. Fry gently for 5 mins or until starting to turn dark brown. Add the tomatoes and butter beans, bring to the boil, then simmer for 10 mins. Swirl through the pesto, season lightly and ladle into four bowls.");
        recipe5.setPreparationTimeMin(17);
        recipe5.setRecipeId(Counter.getValue());
        recipe5.setIngredients(ingredients5);
        recipe5.setTitle("Butter bean and chorizo stew");

        List<Recipe> flights = Arrays.asList(
                recipe1,
                recipe2,
                recipe3,
                recipe4,
                recipe5
        );
        this.mongoTemplate.insertAll(flights);
    }
}
