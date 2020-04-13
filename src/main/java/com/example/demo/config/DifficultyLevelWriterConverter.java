package com.example.demo.config;

import com.example.demo.domain.DifficultyLevel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class DifficultyLevelWriterConverter implements Converter<DifficultyLevel, String> {
    @Override
    public String convert(DifficultyLevel level) {
        return level.name();
    }
}
