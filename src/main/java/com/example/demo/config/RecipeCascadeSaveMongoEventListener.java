package com.example.demo.config;

import com.example.demo.domain.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

public class RecipeCascadeSaveMongoEventListener extends AbstractMongoEventListener<Object> {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        Object source = event.getSource();
        if ((source instanceof Recipe)
                && ((Recipe) source).getIngredients() != null
                && ((Recipe) source).getIngredients().size() > 0
        ) {
            mongoTemplate.insertAll(((Recipe) source).getIngredients());
        }
    }
}
