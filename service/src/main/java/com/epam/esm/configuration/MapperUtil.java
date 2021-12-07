package com.epam.esm.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@ComponentScan("com.epam.esm")
public class MapperUtil {
    @Bean
    public ModelMapper getMapper() {
        return new ModelMapper();
    }
    public static <R, E> List<R> convertList(List<E> list, Function<E, R> converter) {
        return list.stream().map(converter).collect(Collectors.toList());
    }
}