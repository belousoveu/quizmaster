package org.skypro.be.quizmaster.config;

import org.skypro.be.quizmaster.converter.StringToSectionConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final StringToSectionConverter stringToSectionConverter;

    public WebConfig(StringToSectionConverter stringToSectionConverter) {
        this.stringToSectionConverter = stringToSectionConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToSectionConverter);
    }
}