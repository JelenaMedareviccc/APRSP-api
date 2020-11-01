package com.example.project.config;

import com.example.project.GenericModelConverter;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableScheduling
public class WebConfig implements WebMvcConfigurer {
    /**
     * This method is used to tell Spring about new converter, by adding GenericModelConverter to the FormatterRegistry.
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new GenericModelConverter());
    }
    
    
}
