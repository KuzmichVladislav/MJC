package com.epam.esm.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * The Class WebConfiguration contains a spring configuration for web.
 */
@ComponentScan("com.epam.esm")
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(WebConfiguration.class, args);
    }
}
