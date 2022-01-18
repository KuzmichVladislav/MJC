package com.epam.esm.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.epam.esm")
@EntityScan("com.epam.esm")
@EnableAutoConfiguration
public class PersistenceTestConfiguration {
}