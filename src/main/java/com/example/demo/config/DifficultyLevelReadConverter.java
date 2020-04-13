package com.example.demo.config;

import com.example.demo.domain.DifficultyLevel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.util.Locale;

@ReadingConverter
public class DifficultyLevelReadConverter implements Converter<String, DifficultyLevel> {
    @Override
    public DifficultyLevel convert(String level) {
        return DifficultyLevel.valueOf(level.toUpperCase(Locale.ENGLISH));
    }
}
