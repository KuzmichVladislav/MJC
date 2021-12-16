package com.epam.esm.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static org.modelmapper.config.Configuration.AccessLevel;

/**
 * The Class ServiceConfiguration contains a spring configuration for service.
 */
@Configuration
@ComponentScan("com.epam.esm")
public class ServiceConfiguration {

    @Bean
    public ModelMapper getMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldAccessLevel(AccessLevel.PRIVATE)
                .setSkipNullEnabled(true)
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true);
        return modelMapper;
    }
}
