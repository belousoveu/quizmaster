package org.skypro.be.quizmaster.converter;

import org.jetbrains.annotations.NotNull;
import org.skypro.be.quizmaster.model.Section;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSectionConverter implements Converter<String, Section> {

    @Override
    public Section convert(@NotNull String sectionName) {
        return Section.getByName(sectionName);
    }
}