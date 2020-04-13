package com.example.demo.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoDBConfig {
    private final List<Converter<?, ?>> converters = new ArrayList<Converter<?, ?>>();

    @Bean
    public RecipeCascadeSaveMongoEventListener userCascadingMongoEventListener() {
        return new RecipeCascadeSaveMongoEventListener();
    }

    @Bean
    public MongoCustomConversions customConversions() {
        converters.add(new DifficultyLevelReadConverter());
        converters.add(new DifficultyLevelWriterConverter());

        return new MongoCustomConversions(converters);
    }
}
